package cn.wanxh.comsumer.proxy;

import cn.wanxh.comsumer.RpcConsumer;
import cn.wanxh.core.CustomRpcFuture;
import cn.wanxh.core.RpcRequest;
import cn.wanxh.core.RpcRequestHolder;
import cn.wanxh.core.RpcResponse;
import cn.wanxh.protocol.CustomRpcProtocol;
import cn.wanxh.protocol.MsgHeader;
import cn.wanxh.protocol.MsgType;
import cn.wanxh.protocol.ProtocolConstants;
import cn.wanxh.registry.RegistryService;
import cn.wanxh.serialization.SerializationType;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @program: rpc-netty
 * @Date: 2022/8/8 0:49
 * @Author: 阿左不是蜗牛
 * @Description: 执行代理逻辑
 */
public class RpcInvokerProxy implements InvocationHandler {

    private final String serviceVersion;

    private final long timeout;

    private final RegistryService registryService;

    public RpcInvokerProxy(String serviceVersion, long timeout, RegistryService registryService) {
        this.serviceVersion = serviceVersion;
        this.timeout = timeout;
        this.registryService = registryService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        final CustomRpcProtocol<RpcRequest> protocol = new CustomRpcProtocol<>();
        final MsgHeader header = new MsgHeader();
        final long requestId = RpcRequestHolder.REQUEST_ID_GEN.incrementAndGet();
        header.setMagic(ProtocolConstants.MAGIC);
        header.setVersion(ProtocolConstants.VERSION);
        header.setRequestId(requestId);
        header.setSerialization((byte) SerializationType.HESSIAN.getType());
        header.setMsgType((byte) MsgType.REQUEST.getType());
        header.setStatus((byte) 0x1);
        protocol.setHeader(header);

        final RpcRequest request = new RpcRequest();
        request.setServiceVersion(this.serviceVersion);
        request.setClassName(method.getDeclaringClass().getName());
        request.setParameterType(method.getParameterTypes());
        request.setParams(args);
        protocol.setMsgBody(request);

        final RpcConsumer rpcConsumer = new RpcConsumer(); // 每个请求都要new一次？ 或者加锁？ 或者使用ThreadLocal？ 避免同时多个请求出现的并发问题
        final CustomRpcFuture<RpcResponse> future = new CustomRpcFuture<>(new DefaultPromise<>(new DefaultEventLoop()), timeout);
        RpcRequestHolder.REQUEST_MAP.put(requestId, future);
        // 发起请求
        rpcConsumer.sendRequest(protocol, this.registryService);

        return future.getPromise().get(future.getTimeout(), TimeUnit.SECONDS).getData();

        // ThreadLocal持有request的引用

    }
}
