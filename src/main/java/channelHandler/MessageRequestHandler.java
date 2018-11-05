package channelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.PacketCodeC;
import protocal.model.MessageRequestPacket;
import protocal.model.MessageResponsePacket;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket packet) throws Exception {
        channelHandlerContext.channel().writeAndFlush(handleMessage(packet));
    }

    //接收到客户端对话消息
    private MessageResponsePacket handleMessage(MessageRequestPacket packet) {
        String message = packet.getMessage();
        System.out.println("客户端说：" + message);
        MessageResponsePacket mpPacket = new MessageResponsePacket();
        mpPacket.setMessage("好了好了我收到了，哎...");
        System.out.println("服务端说："  + mpPacket.getMessage());
        return mpPacket;
    }
}
