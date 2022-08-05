package cn.wanxh.serialization;

import java.io.IOException;

public class JsonSerialization implements RpcSerialization{
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return null;
    }
}
