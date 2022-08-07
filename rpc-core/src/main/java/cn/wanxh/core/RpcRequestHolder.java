package cn.wanxh.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: rpc-netty
 * @Date: 2022/8/7 23:26
 * @Author: 阿左不是蜗牛
 * @Description:
 */
public class RpcRequestHolder {
    public static final AtomicLong REQUEST_ID_GEN = new AtomicLong(0);

    public static final Map<Long, CustomRpcFuture<RpcResponse>> REQUEST_MAP =new ConcurrentHashMap<>();

}
