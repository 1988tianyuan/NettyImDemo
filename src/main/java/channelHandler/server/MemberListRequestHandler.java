package channelHandler.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocal.model.MemberListRequestPacket;
import protocal.model.MemberListResponsePacket;
import protocal.model.Session;
import utils.SessionUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MemberListRequestHandler extends SimpleChannelInboundHandler<MemberListRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MemberListRequestPacket packet) throws Exception {
        String groupId = packet.getGroupId();
        ChannelGroup group = SessionUtil.getChannelGroup(groupId);
        List<String> nameList = new LinkedList<>();
        Iterator<Channel> channelIterator = group.iterator();
        while (channelIterator.hasNext()) {
            Channel channel = channelIterator.next();
            nameList.add(SessionUtil.getSession(channel).getUserName());
        }
        MemberListResponsePacket responsePacket = new MemberListResponsePacket();
        responsePacket.setMemberList(nameList);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
