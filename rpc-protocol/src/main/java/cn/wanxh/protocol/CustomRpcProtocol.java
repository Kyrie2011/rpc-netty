package cn.wanxh.protocol;

import java.io.Serializable;

public class CustomRpcProtocol<T> implements Serializable {

    private T msgBody;
}
