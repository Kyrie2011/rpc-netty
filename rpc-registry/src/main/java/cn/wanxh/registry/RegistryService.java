package cn.wanxh.registry;


import cn.wanxh.core.ServiceMeta;

import java.io.IOException;

/**
 * @program: rpc-netty
 * @Date: 2022/8/5 0:27
 * @Author: 阿左不是蜗牛
 * @Description: 注册中心相关api: 服务暴露、发现、下线等
 */
public interface RegistryService {

    void register(ServiceMeta serviceMeta) throws Exception;

    void unRegister(ServiceMeta serviceMeta) throws Exception;

    ServiceMeta discovery(String serviceName, int invokerHashCode) throws Exception;

    void destroy() throws IOException;

}
