package channelHandler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.model.HeartBeatRequestPacket;
import protocal.model.MessageResponsePacket;

public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket packet) throws Exception {
        System.out.println("收到心跳：" + packet.getHeartBeatMsg());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("好的知道了");
        messageResponsePacket.setFromUserId("0");
        messageResponsePacket.setFromUserName("系统管理员");
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
