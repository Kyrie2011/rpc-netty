package cn.wanxh.comsumer;

import cn.wanxh.comsumer.proxy.RpcInvokerProxy;
import cn.wanxh.registry.RegistryFactory;
import cn.wanxh.registry.RegistryService;
import cn.wanxh.registry.RegistryType;
import com.sun.org.apache.xml.internal.security.Init;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

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
        return interfaceClass;  // 标记哪些类可以通过该工厂bean来获取bean对象
    }

    // init()方法被设置为initMethod,见builder.setInitMethodName(RpcConstants.INIT_METHOD_NAME);
    public void init() throws Exception{
        // 生成代理对象
        final RegistryService registryService = RegistryFactory.getInstance(this.registryAddress, RegistryType.valueOf(this.registryType));
        this.object = Proxy.newProxyInstance(
          interfaceClass.getClassLoader(),
          new Class<?>[]{interfaceClass},
          new RpcInvokerProxy(serviceVersion, timeout, registryService)
        );


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
