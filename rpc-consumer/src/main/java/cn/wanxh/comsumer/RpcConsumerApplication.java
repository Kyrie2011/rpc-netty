package cn.wanxh.comsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @program: rpc-netty
 * @Date: 2022/8/7 23:43
 * @Author: 阿左不是蜗牛
 * @Description: 消费端启动类
 */
@EnableConfigurationProperties
@SpringBootApplication
public class RpcConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcConsumerApplication.class, args);
    }
}
