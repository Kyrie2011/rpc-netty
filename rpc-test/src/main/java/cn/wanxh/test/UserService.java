package cn.wanxh.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserService {

    @Autowired
    @Resource(name = "orderService1")
    private OrderService orderService;

    @Bean("orderService1")
    public OrderService orderService1(){
        OrderService orderService = new OrderService();
        orderService.setDesc("update");
        return orderService;
    }

    public void test() {
        System.out.println("test:" + orderService);
    }

}
