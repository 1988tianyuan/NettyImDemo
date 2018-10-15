package protocal;

import protocal.support.JSONserializer;

public interface Serializer {

    Serializer DEFAULT = new JSONserializer();

    //序列化算法
    byte getSerializerAlgorithm();

    //序列化
    byte[] serialize(Object object);

    //反序列化
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
