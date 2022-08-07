package cn.wanxh.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @program: rpc-netty
 * @Date: 2022/8/4 23:52
 * @Author: 阿左不是蜗牛
 * @Description: TODO
 */
@EnableConfigurationProperties
@SpringBootApplication
public class RpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcProviderApplication.class, args);
    }

}
