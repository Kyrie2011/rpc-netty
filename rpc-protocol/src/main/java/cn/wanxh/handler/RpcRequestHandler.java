package cn.wanxh.handler;

import cn.wanxh.core.RpcRequest;
import cn.wanxh.protocol.CustomRpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcRequestHandler extends SimpleChannelInboundHandler<CustomRpcProtocol<RpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CustomRpcProtocol<RpcRequest> msg) throws Exception {
        RpcRequestProcessor.submitRequest(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
