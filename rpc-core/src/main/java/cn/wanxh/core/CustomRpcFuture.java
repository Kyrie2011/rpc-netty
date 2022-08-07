package cn.wanxh.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

/**
 * @program: rpc-netty
 * @Date: 2022/8/7 23:27
 * @Author: 阿左不是蜗牛
 * @Description: TODO
 */
@Data
public class CustomRpcFuture<T> {
    private Promise<T> promise;

    private long timeout;

    public CustomRpcFuture(Promise<T> promise, long timeout) {
        this.promise = promise;
        this.timeout = timeout;
    }
}
