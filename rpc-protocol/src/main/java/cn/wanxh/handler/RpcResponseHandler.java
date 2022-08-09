package cn.wanxh.handler;

import cn.wanxh.core.CustomRpcFuture;
import cn.wanxh.core.RpcRequestHolder;
import cn.wanxh.core.RpcResponse;
import cn.wanxh.protocol.CustomRpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @program: rpc-netty
 * @Date: 2022/8/7 22:55
 * @Author: 阿左不是蜗牛
 * @Description: 响应处理handler
 */
public class RpcResponseHandler extends SimpleChannelInboundHandler<CustomRpcProtocol<RpcResponse>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomRpcProtocol<RpcResponse> msg) throws Exception {
        long requestId = msg.getHeader().getRequestId();
        CustomRpcFuture<RpcResponse> future = RpcRequestHolder.REQUEST_MAP.remove(requestId);  // 移除时机
        future.getPromise().setSuccess(msg.getMsgBody());
    }
}
