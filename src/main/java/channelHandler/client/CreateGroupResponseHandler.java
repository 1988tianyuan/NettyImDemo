package channelHandler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.model.CreateGroupResponsePacket;
import protocal.model.Session;
import utils.Constants;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket packet) throws Exception {
        System.out.println("创建群聊成功，id是：" + packet.getGroupId());
        System.out.println("群里有：" + packet.getUserNameList());
        Session session = ctx.channel().attr(Constants.SESSION).get();
        session.setGroupId(packet.getGroupId());
    }
}
