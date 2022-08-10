package cn.wanxh.core.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @program: rpc-netty
 * @Date: 2022/8/10 23:39
 * @Author: 阿左不是蜗牛
 * @Description: 心跳检测，客户端向服务端定时发送心跳包，服务端收到后并不回响应回复，
 * 因为如果同时与服务端建立的客户端连接规模较大，响应心跳数据需要消耗一定的资源。
 */
public class RpcHeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        doHeartBeatTask(ctx);
    }

    private void doHeartBeatTask(ChannelHandlerContext ctx) {

        ctx.executor().schedule( () -> {
            if (ctx.channel().isActive()) {
                // 构建心跳包数据
                // ctx.writeAndFlush(heartBeatData);
            }
        }, 10, TimeUnit.SECONDS);

    }
}
