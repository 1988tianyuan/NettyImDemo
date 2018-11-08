package channelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.PacketCodeC;
import protocal.model.MessageRequestPacket;
import protocal.model.MessageResponsePacket;
import protocal.model.Session;
import utils.Constants;
import utils.SessionUtil;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket packet) throws Exception {
        Channel currentChannel = channelHandlerContext.channel();
        Session session = currentChannel.attr(Constants.SESSION).get();
        //构建回话
        MessageResponsePacket rspPacket = new MessageResponsePacket();
        rspPacket.setFromUserId(session.getUserId());
        rspPacket.setFromUserName(session.getUserName());
        rspPacket.setMessage(packet.getMessage());

        Channel toChannel = SessionUtil.getChannel(packet.getToUserId());
        if(toChannel !=null && toChannel.hasAttr(Constants.SESSION)) {
            toChannel.writeAndFlush(rspPacket);
        }else {
            rspPacket.setMessage("error：他已经不在线啦！！！");
            currentChannel.writeAndFlush(rspPacket);
        }
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
