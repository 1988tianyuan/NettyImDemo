package channelHandler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocal.model.JoinGroupRequestPacket;
import protocal.model.JoinGroupResponsePacket;
import protocal.model.MessageResponsePacket;
import protocal.model.Session;
import utils.SessionUtil;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket packet) throws Exception {
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        String groupId = packet.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        Session session = SessionUtil.getSession(ctx.channel());
        if(channelGroup != null) {
            joinGroupResponsePacket.setMsg(String.format("加入群聊[%s]成功", groupId));
            joinGroupResponsePacket.setSuccess(true);
            joinGroupResponsePacket.setGroupId(groupId);
            //将加入群聊信息推送给群内成员
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setFromUserId("0");
            messageResponsePacket.setFromUserName("系统管理员");
            messageResponsePacket.setMessage(String.format("[%s: %s]加入群聊", session.getUserId(), session.getUserName()));
            channelGroup.writeAndFlush(messageResponsePacket);
            channelGroup.add(ctx.channel());
        }else {
            joinGroupResponsePacket.setMsg(String.format("加入群聊[%s]失败，该群不存在！", groupId));
            joinGroupResponsePacket.setSuccess(false);
        }
        ctx.channel().writeAndFlush(joinGroupResponsePacket);
    }
}
