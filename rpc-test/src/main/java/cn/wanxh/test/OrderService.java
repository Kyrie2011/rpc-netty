package cn.wanxh.test;

import org.springframework.stereotype.Component;

@Component
public class OrderService {
    private String desc = "default";

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderService{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
