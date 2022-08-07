package cn.wanxh.registry.loadbalancer;

import java.util.List;

/**
 * @program: rpc-netty
 * @Date: 2022/8/8 1:02
 * @Author: 阿左不是蜗牛
 * @Description: 负载均衡接口
 */
public interface ServiceLoadBalancer<T> {
    T select(List<T> servers, int hashCode);
}
