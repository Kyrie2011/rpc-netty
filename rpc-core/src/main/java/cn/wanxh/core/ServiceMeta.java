package cn.wanxh.core;

import lombok.Data;

/**
 * @program: rpc-netty
 * @Date: 2022/8/5 0:27
 * @Author: 阿左不是蜗牛
 * @Description: 服务元数据信息
 */
@Data
public class ServiceMeta {

    private String serviceName;

    private String serviceVersion;

    private String serviceAddress;

    private int servicePort;


}
