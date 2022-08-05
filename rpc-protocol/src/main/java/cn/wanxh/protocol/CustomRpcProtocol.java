package cn.wanxh.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomRpcProtocol<T> implements Serializable {

    private MsgHeader header;

    private T msgBody;
}
