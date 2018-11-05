package channelHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocal.model.MessageRequestPacket;
import protocal.model.MessageResponsePacket;
import utils.LoginUtil;

import java.util.Scanner;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    private static Logger logger = LoggerFactory.getLogger(MessageResponseHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        String message = messageResponsePacket.getMessage();
        System.out.println("服务器端回复：" + message);
    }
}
