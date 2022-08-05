package cn.wanxh.handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 业务处理线程池
 */
public class RpcRequestProcessor {
    private static volatile ThreadPoolExecutor threadPoolExecutor;  // 单例

    public static void submitRequest(Runnable task){
        if (threadPoolExecutor == null) {
            synchronized (RpcRequestProcessor.class) {
                if (threadPoolExecutor == null ){
                    threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
                }
            }
        }
        threadPoolExecutor.submit(task);
    }
}
