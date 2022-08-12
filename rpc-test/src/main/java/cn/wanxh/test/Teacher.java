package cn.wanxh.test;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;


@Component
public class Teacher implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new UserInfo();
    }

    @Override
    public Class<?> getObjectType() {
        return UserInfo.class;
    }
}
