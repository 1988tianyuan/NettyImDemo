package channelHandler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocal.model.GroupMessageRequestPacket;
import utils.Constants;
import utils.SessionUtil;

public class GroupMsgRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket packet) throws Exception {
        String groupId = packet.getGroupId();
        ChannelGroup group = SessionUtil.getChannelGroup(groupId);
        group.writeAndFlush(packet);
    }
}
