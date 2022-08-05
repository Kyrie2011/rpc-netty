package cn.wanxh.serialization;

public class SerializationFactory {

    public static RpcSerialization getRpcSerialization(byte serializationType) {

        SerializationType type = SerializationType.findByType(serializationType);

        switch (type) {
            case JSON:
                return new JsonSerialization();
            case HESSIAN:
                return new HessianSerialization();
            default:
                throw new IllegalArgumentException("serialization type is illegal, " + serializationType);

        }

    }

}
