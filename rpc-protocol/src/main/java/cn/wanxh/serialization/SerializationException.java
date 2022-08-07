package cn.wanxh.serialization;

/**
 * @program: rpc-netty
 * @Date: 2022/8/7 22:16
 * @Author: 阿左不是蜗牛
 * @Description: 自定义序列化异常
 */
public class SerializationException extends RuntimeException{
    public SerializationException(){
        super();
    }

    public SerializationException(String msg){
        super(msg);
    }

    public SerializationException(String msg, Throwable cause){
        super(msg, cause);
    }

    public SerializationException(Throwable cause){
        super(cause);
    }
}
