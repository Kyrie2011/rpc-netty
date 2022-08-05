package cn.wanxh.serialization;

import lombok.Getter;

public enum SerializationType {
    HESSIAN(0x10),
    JSON(0x20);

    @Getter
    private final int type;

    SerializationType(int type) {
        this.type = type;
    }

    public static SerializationType findByType(byte serializationType){
        SerializationType[] types = SerializationType.values();
        for (SerializationType type : types) {
            if (type.getType() == serializationType){
                return type;
            }
        }
        return HESSIAN;
    }
}
