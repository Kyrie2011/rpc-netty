package cn.wanxh.comsumer;

import cn.wanxh.codec.CustomRpcDecoder;
import cn.wanxh.codec.CustomRpcEncoder;
import cn.wanxh.handler.RpcResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
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


}
