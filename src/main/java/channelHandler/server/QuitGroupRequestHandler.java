package channelHandler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocal.model.MessageResponsePacket;
import protocal.model.QuitGroupRequestPacket;
import protocal.model.QuitGroupResponsePacket;
import protocal.model.Session;
import utils.SessionUtil;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket packet) throws Exception {
        String groupId = packet.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        Session session = SessionUtil.getSession(ctx.channel());
        boolean isQuit = channelGroup.remove(ctx.channel());

        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        if(isQuit) {
            quitGroupResponsePacket.setGroupId(groupId);
            quitGroupResponsePacket.setMsg("退出群聊成功");
            //将退出群聊信息推送给群内成员
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setFromUserId("0");
            messageResponsePacket.setFromUserName("系统管理员");
            messageResponsePacket.setMessage(String.format("[%s: %s]退出群聊", session.getUserId(), session.getUserName()));
            channelGroup.writeAndFlush(messageResponsePacket);
        }else {
            quitGroupResponsePacket.setMsg(String.format("退出群聊[%s]失败，你不在这个群里", groupId));
        }
        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
