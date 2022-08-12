package cn.wanxh.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test3 {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean.class);

        // 获取的是getObject方法返回的对象
        System.out.println(context.getBean("teacher"));
        // 获取的是getObject方法所属类的对象
        //System.out.println(context.getBean("&teacher"));
    }
}
