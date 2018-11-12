package channelHandler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.model.GroupMessageRequestPacket;

public class GroupMsgResponseHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket packet) throws Exception {
        String fromUser = packet.getFromUser();
        String groupId = packet.getGroupId();
        String msg = packet.getMsg();
        System.out.println(String.format("来自群组[%s]成员[%s]的消息：%s", groupId, fromUser, msg));
    }
}
