package protocal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import protocal.model.LoginRequestPacket;
import protocal.model.Packet;
import protocal.support.JSONserializer;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;

    //对象编码，返回
    public ByteBuf encode(Packet packet){
        //创建ByteBuf, ioBuffer()是在direct memory中创建
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        //序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //写入数据
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Object decode(ByteBuf byteBuf){
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);

        byte algorithmCode = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();

        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Class<? extends Packet> requireType = getRequireClass(command);
        Serializer serializer = getSerializer(algorithmCode);

        if(requireType != null && serializer != null){
            return serializer.deserialize(requireType, data);
        }

        return null;
    }

    private Class<? extends Packet> getRequireClass(byte command) {
        switch (command){
            case 1:
                return LoginRequestPacket.class;
            default:
                return null;
        }
    }

    private Serializer getSerializer(byte algorithmCode){
        switch (algorithmCode){
            case 1:
                return new JSONserializer();
            default:
                return null;
        }
    }

}
