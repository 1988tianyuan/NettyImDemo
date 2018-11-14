package protocal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import protocal.model.*;
import protocal.support.JSONserializer;
import sun.awt.geom.AreaOp;

public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;

    //对象编码，返回
    public static ByteBuf encode(ByteBuf byteBuf, Packet packet){
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

    public static Packet decode(ByteBuf byteBuf){
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

    private static Class<? extends Packet> getRequireClass(byte command) {
        switch (command){
            case 1:
                return LoginRequestPacket.class;
            case 2:
                return LoginResponsePacket.class;
            case 3:
                return MessageRequestPacket.class;
            case 4:
                return MessageResponsePacket.class;
            case 5:
                return CreateGroupRequestPacket.class;
            case 6:
                return CreateGroupResponsePacket.class;
            case 7:
                return JoinGroupRequestPacket.class;
            case 8:
                return JoinGroupResponsePacket.class;
            case 9:
                return QuitGroupRequestPacket.class;
            case 10:
                return QuitGroupResponsePacket.class;
            case 11:
                return MemberListRequestPacket.class;
            case 12:
                return MemberListResponsePacket.class;
            case 13:
                return GroupMessageRequestPacket.class;
            case 14:
                return LogoutRequestPacket.class;
            case 15:
                return HeartBeatRequestPacket.class;
            default:
                return null;
        }
    }

    private static Serializer getSerializer(byte algorithmCode){
        switch (algorithmCode){
            case 1:
                return new JSONserializer();
            default:
                return null;
        }
    }

}
