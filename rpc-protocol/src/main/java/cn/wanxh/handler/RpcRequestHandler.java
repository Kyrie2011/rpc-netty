package cn.wanxh.handler;

import cn.wanxh.core.RpcRequest;
import cn.wanxh.core.RpcResponse;
import cn.wanxh.core.RpcServiceHelper;
import cn.wanxh.protocol.CustomRpcProtocol;
import cn.wanxh.protocol.MsgHeader;
import cn.wanxh.protocol.MsgStatus;
import cn.wanxh.protocol.MsgType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.reflect.FastClass;

import java.util.Map;


@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<CustomRpcProtocol<RpcRequest>> {

    private final Map<String, Object> rpcServiceMap;

    public RpcRequestHandler(Map<String, Object> rpcServiceMap) {
        this.rpcServiceMap = rpcServiceMap;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomRpcProtocol<RpcRequest> msg) throws Exception {
        /**
         * 通过业务线程池处理。
         * TODO：ps后期优化成线程池隔离：根据业务逻辑的核心等级拆分出多个业务线程池，
         * 如果某类业务逻辑出现异常造成线程池资源耗尽，也不会影响到其他业务逻辑
         * 从而提高应用程序整体可用性
         */
        RpcRequestProcessor.submitRequest(new Runnable() {
            @Override
            public void run() {
                CustomRpcProtocol<RpcResponse> responseProtocol = new CustomRpcProtocol<>();
                RpcResponse response = new RpcResponse();
                responseProtocol.setMsgBody(response);
                MsgHeader header = msg.getHeader();
                header.setMsgType((byte) MsgType.RESPONSE.getType());
                responseProtocol.setHeader(header);
                try{
                    // 业务处理
                    Object result = handle(msg.getMsgBody());
                }catch(Throwable throwable){
                    header.setStatus((byte) MsgStatus.FAIL.getCode());
                    response.setMsg(throwable.toString());
                    log.error("process request {} error", header.getRequestId(), throwable);
                }
                // 传递给下个handler处理
                ctx.writeAndFlush(responseProtocol);
            }


        });

    }

    private Object handle (RpcRequest request) throws Throwable {

        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getServiceVersion());
        // 从缓存中获取服务Bean对象
        Object serviceBean = rpcServiceMap.get(serviceKey);

        if (serviceBean == null) {
            throw new RuntimeException(String.format("service not exist: %s:%s", request.getClassName(), request.getMethodName()));
        }
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterType();
        Object[] params = request.getParams();

        /**
         *  Method method = serviceBean.getClass().getMethod(methodName, parameterTypes);
         *  Object returnValue = method.invoke(serviceBean, params);
        */

        FastClass fastClass = FastClass.create(serviceClass);
        int methodIndex = fastClass.getIndex(methodName, parameterTypes);
        return fastClass.invoke(methodIndex, serviceBean, params); // 比直接反射调用效率快
    }
}
