package com.kingdombiao.rpc.serializer;

/**
 * 序列化工具接口
 */
public interface Serializer {

    /**
     * 序列化
     *
     * @param obj
     */
    byte[] serialize(Object obj);


    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @return
     */
    <T> T deserialize(byte[] bytes);
}
