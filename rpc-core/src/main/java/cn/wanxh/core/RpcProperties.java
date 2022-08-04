package cn.wanxh.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: rpc-netty
 * @Date: 2022/8/5 0:16
 * @Author: 阿左不是蜗牛
 * @Description: TODO
 */

@Data
@ConfigurationProperties(prefix = "rpc")
public class RpcProperties {
    /** 服务暴露的端口 **/
    private int servicePort;
    /** 注册中心的地址 **/
    private String registryAddress;
    /** 注册中心的类型 **/
    private String registryType;
}
