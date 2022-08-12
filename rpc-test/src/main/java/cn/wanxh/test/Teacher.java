package cn.wanxh.test;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;


@Component
public class Teacher implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new UserInfo(); // 不被Spring容器管理？，容器中也就不存UserInfo对应的BeanDefinition信息。生命周期简化，没有复杂的实例化和属性注入的过程，可以在初始化前后对其进行扩展
    }

    @Override
    public Class<?> getObjectType() {
        return UserInfo.class;
    }
}
