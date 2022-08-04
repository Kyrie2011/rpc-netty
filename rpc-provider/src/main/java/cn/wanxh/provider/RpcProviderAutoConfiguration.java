package cn.wanxh.provider;

import cn.wanxh.core.RpcProperties;
import cn.wanxh.core.ServiceMeta;
import cn.wanxh.registry.RegistryFactory;
import cn.wanxh.registry.RegistryService;
import cn.wanxh.registry.RegistryType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @program: rpc-netty
 * @Date: 2022/8/5 0:19
 * @Author: 阿左不是蜗牛
 * @Description: TODO
 */
@Configuration
@EnableConfigurationProperties(RpcProperties.class)
public class RpcProviderAutoConfiguration {

    @Resource
    private RpcProperties rpcProperties;

    @Bean
    public RpcProvider init() throws Exception{
        RegistryType type = RegistryType.valueOf(rpcProperties.getRegistryType());
        RegistryService serviceRegistry = RegistryFactory.getInstance(rpcProperties.getRegistryAddress(), type);

        return new RpcProvider(rpcProperties.getServicePort(), serviceRegistry);
    }

}
