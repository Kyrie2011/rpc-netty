package cn.wanxh.core.handler;

import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @program: rpc-netty
 * @Date: 2022/8/10 23:54
 * @Author: 阿左不是蜗牛
 * @Description: 空闲检测
 */
public class RpcIdleStateHandler extends IdleStateHandler {

    public RpcIdleStateHandler() {

        super(60, 0, 0, TimeUnit.SECONDS);

    }

}
