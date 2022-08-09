package cn.wanxh.comsumer;

import cn.wanxh.codec.CustomRpcDecoder;
import cn.wanxh.codec.CustomRpcEncoder;
import cn.wanxh.core.RpcRequest;
import cn.wanxh.core.RpcServiceHelper;
import cn.wanxh.core.ServiceMeta;
import cn.wanxh.handler.RpcResponseHandler;
import cn.wanxh.protocol.CustomRpcProtocol;
import cn.wanxh.registry.RegistryService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: rpc-netty
 * @Date: 2022/8/7 22:46
 * @Author: 阿左不是蜗牛
 * @Description: 客户端Netty启动程序
 */
@Slf4j
public class RpcConsumer {

    private final Bootstrap bootstrap;

    private final EventLoopGroup eventLoopGroup;

    public RpcConsumer() {
        this.bootstrap = new Bootstrap();
        this.eventLoopGroup = new NioEventLoopGroup();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new CustomRpcEncoder());
                        pipeline.addLast(new CustomRpcDecoder());
                        pipeline.addLast(new RpcResponseHandler());

                    }
                });

    }

    public void sendRequest(CustomRpcProtocol<RpcRequest> protocol, RegistryService registryService) throws Exception{
        RpcRequest request = protocol.getMsgBody();
        final Object[] params = request.getParams();
        final String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getServiceVersion());
        int invokeHashCode = params.length > 0 ? params[0].hashCode() : serviceKey.hashCode();
        // 服务发现 + 负载均衡
        final ServiceMeta serviceMeta = registryService.discovery(serviceKey, invokeHashCode);

        if (serviceMeta != null) {
            final ChannelFuture future = bootstrap.connect(serviceMeta.getServiceAddress(), serviceMeta.getServicePort()).sync();
            future.addListener((ChannelFutureListener) arg0-> {
                if (future.isSuccess()){
                    log.info("connect rpc server {} on port {} success.", serviceMeta.getServiceAddress(), serviceMeta.getServicePort());
                }else{
                    log.error("connect rpc server {} on port {} failed.", serviceMeta.getServiceAddress(), serviceMeta.getServicePort());
                    future.cause().printStackTrace();
                    eventLoopGroup.shutdownGracefully();
                }

            });
            // 写出数据
            future.channel().writeAndFlush(protocol);

        }


    }


}
