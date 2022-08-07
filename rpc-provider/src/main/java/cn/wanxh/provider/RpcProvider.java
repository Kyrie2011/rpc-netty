package cn.wanxh.provider;

import cn.wanxh.core.RpcServiceHelper;
import cn.wanxh.core.ServiceMeta;
import cn.wanxh.codec.CustomRpcDecoder;
import cn.wanxh.codec.CustomRpcEncoder;
import cn.wanxh.handler.RpcRequestHandler;
import cn.wanxh.provider.annotation.RpcService;
import cn.wanxh.registry.RegistryService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: rpc-netty
 * @Date: 2022/8/5 0:22
 * @Author: 阿左不是蜗牛
 * @Description: TODO
 */
@Slf4j
public class RpcProvider implements InitializingBean, BeanPostProcessor {
    private int servicePort;
    private RegistryService serviceRegistry;
    private String serviceAddress;

    Map<String, Object> rpcServiceMap = new ConcurrentHashMap<>();


    public RpcProvider(int servicePort, RegistryService serviceRegistry) {
        this.servicePort = servicePort;
        this.serviceRegistry = serviceRegistry;
    }


    /**
     * 初始化后，扫描@RpcService的注解
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if (rpcService != null) {
            String serviceName = rpcService.serviceInterface().getName();
            String serviceVersion = rpcService.serviceVersion();
            try {
                ServiceMeta serviceMeta = new ServiceMeta();
                serviceMeta.setServiceAddress(serviceAddress);
                serviceMeta.setServicePort(servicePort);
                serviceMeta.setServiceName(serviceName);
                serviceMeta.setServiceVersion(serviceVersion);

                serviceRegistry.register(serviceMeta); // 注册服务元数据信息
                // 缓存serviceBean对象
                rpcServiceMap.put(RpcServiceHelper.buildServiceKey(serviceName, serviceVersion), bean);

            }catch (Exception e){
                log.error("failed to register service {}#{}", serviceName, serviceVersion, e);
            }
        }

        return bean;
    }

    /**
     * 初始化Bean的时候执行
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            try {
                // 启动server端的netty程序
                startRpcServer();
            }catch (Exception e) {

            }
        }).start();
    }

    private void startRpcServer() throws Exception{
        this.serviceAddress = InetAddress.getLocalHost().getHostAddress();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 增加handler
                            //1. 请求解码
                           pipeline.addLast(new CustomRpcDecoder());
                            //2. 响应编码
                            pipeline.addLast(new CustomRpcEncoder());
                            //3. 请求处理
                            pipeline.addLast(new RpcRequestHandler(rpcServiceMap));
                        }
                    }).childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = serverBootstrap.bind(this.serviceAddress, this.servicePort).sync();
            log.info("server address {} started on port {}", this.serviceAddress, this.servicePort);
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
