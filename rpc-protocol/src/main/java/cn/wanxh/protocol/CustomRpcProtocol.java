package cn.wanxh.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义消息协议
 * @param <T>
 */
@Data
public class CustomRpcProtocol<T> implements Serializable {

    private MsgHeader header;

    private T msgBody;
}
