package com.kingdombiao.rpc.serializer;

/**
 * 序列化工具类工厂实现
 */
public class KryoSerializerFactory {
    public static Serializer getSerializer(Class<?> targetClass){
        return new KryoSerializer(targetClass);
    }
}
