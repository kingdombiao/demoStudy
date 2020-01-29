package com.kingdombiao.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 基于kryo的序列化/反序列化工具
 */
public class KryoSerializer implements Serializer {
    //需要序列化的对象
    private final Class<?> clazzTarget;

    /**
     * kryo 是非线程安全类
     */
    private final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.register(clazzTarget, new BeanSerializer(kryo, clazzTarget));
            return kryo;
        }
    };

    public KryoSerializer(Class<?> clazzTarget) {
        this.clazzTarget = clazzTarget;
    }

    public Class<?> getClazzTarget() {
        return clazzTarget;
    }

    private Kryo getKryo() {
        return kryoLocal.get();
    }

    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    @Override
    public byte[] serialize(Object obj) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Output output = new Output(outputStream);
        try {
            Kryo kryo = getKryo();
            kryo.writeObjectOrNull(output, obj, obj.getClass());
            output.flush();
            return outputStream.toByteArray();
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @return
     */
    @Override
    public <T> T deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(inputStream);
        try {
            Kryo kryo = getKryo();
            return (T) kryo.readObjectOrNull(input, clazzTarget);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(inputStream);
        }
    }
}
