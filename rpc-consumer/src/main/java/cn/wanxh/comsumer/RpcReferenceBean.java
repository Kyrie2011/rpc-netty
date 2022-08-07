package cn.wanxh.comsumer;

import cn.wanxh.registry.RegistryFactory;
import com.sun.org.apache.xml.internal.security.Init;
import org.springframework.beans.factory.FactoryBean;

/**
 * @program: rpc-netty
 * @Date: 2022/8/8 0:39
 * @Author: 阿左不是蜗牛
 * @Description: 自定义工厂bean
 */
public class RpcReferenceBean implements FactoryBean<Object> {

    private Class<?> interfaceClass;

    private String serviceVersion;

    private String registryType;

    private String registryAddress;

    private long timeout;

    private Object object;


    @Override
    public Object getObject() throws Exception {
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    // init()方法 在构造？的时候填充/执行？
    public void init() throws Exception{
        // 生成代理对象
        // RegistryFactory.getInstance(this.registryAddress)

    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
