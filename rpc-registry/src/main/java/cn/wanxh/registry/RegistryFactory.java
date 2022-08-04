package cn.wanxh.registry;

/**
 * @program: rpc-netty
 * @Date: 2022/8/5 0:35
 * @Author: 阿左不是蜗牛
 * @Description: TODO
 */
public class RegistryFactory {
    /** 单例对象 **/
    private static volatile RegistryService registryService;

    public static RegistryService getInstance(String registryAddress, RegistryType type) throws Exception {

        if (null == registryService) {
            // 双校验锁
            synchronized (RegistryFactory.class) {
                if (null == registryService) {
                    switch (type) {
                        case ZOOKEEPER:
                            registryService = new ZookeeperRegistryService(registryAddress);
                            break;
                    }
                }
            }
        }
        return registryService;
    }


}
