package channelHandler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.PacketCodeC;
import protocal.model.MessageRequestPacket;
import protocal.model.MessageResponsePacket;
import protocal.model.Session;
import utils.Constants;
import utils.SessionUtil;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();
    private MessageRequestHandler(){}

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
            //writeAndFlush是一个异步方法
            toChannel.writeAndFlush(rspPacket);
        }else {
            rspPacket.setMessage("error：他已经不在线啦！！！");
            currentChannel.writeAndFlush(rspPacket);
        }
    }
}
