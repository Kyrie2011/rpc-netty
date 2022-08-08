package cn.wanxh.registry;

import cn.wanxh.core.ServiceMeta;

import java.io.IOException;

/**
 * @program: rpc-netty
 * @Date: 2022/8/5 0:36
 * @Author: 阿左不是蜗牛
 * @Description: TODO
 */
public class ZookeeperRegistryService implements RegistryService{

    public ZookeeperRegistryService(String registryAddress){

    }

    @Override
    public void register(ServiceMeta serviceMeta) throws Exception {


    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) throws Exception {

    }

    @Override
    public ServiceMeta discovery(String serviceKey, int invokerHashCode) throws Exception {


        return null;
    }

    @Override
    public void destroy() throws IOException {

    }
}
