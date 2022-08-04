package cn.wanxh.provider.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: rpc-netty
 * @Date: 2022/8/4 22:45
 * @Author: 阿左不是蜗牛
 * @Description: 标记服务注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RpcService {
    /** 服务类型 **/
    Class<?> serviceInterface() default Object.class;
    /** 服务版本 **/
    String serviceVersion() default "1.0";
}
