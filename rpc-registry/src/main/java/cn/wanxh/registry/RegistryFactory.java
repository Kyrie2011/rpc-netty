package cn.wanxh.registry;

/**
 * @program: rpc-netty
 * @Date: 2022/8/5 0:35
 * @Author: 阿左不是蜗牛
 * @Description: TODO
 */
public class RegistryFactory {
    /** 单例对象 **/
    private static volatile RegistryService registryService;  // static修饰的变量，只会初始化一次

    public static RegistryService getInstance(String registryAddress, RegistryType type) throws Exception {

        if (null == registryService) {
            // 双校验锁 - 单例模式
            synchronized (RegistryFactory.class) {
                if (null == registryService) {
                    switch (type) {
                        case ZOOKEEPER:
                            registryService = new ZookeeperRegistryService(registryAddress);  // 加上volatile，防止指令重排
                            break;
                    }
                }
            }
        }
        return registryService;
    }


}
