package channelHandler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.model.JoinGroupResponsePacket;
import protocal.model.Session;
import utils.Constants;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket packet) throws Exception {
        Session session = ctx.channel().attr(Constants.SESSION).get();
        System.out.println(packet.getMsg());
        if(packet.isSuccess()) {
            String groupId = packet.getGroupId();
            session.setGroupId(groupId);
        }
    }
}
