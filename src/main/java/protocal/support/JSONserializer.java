package protocal.support;

import com.alibaba.fastjson.JSON;
import protocal.Serializer;
import protocal.SerializerAlgorithm;

public class JSONserializer implements Serializer {

    //对应的序列化算法
    private final SerializerAlgorithm serializerAlgorithm = SerializerAlgorithm.JSON;

    @Override
    public byte getSerializerAlgorithm() {
        return serializerAlgorithm.getValue();
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
