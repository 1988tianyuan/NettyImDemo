package channelHandler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.model.MemberListResponsePacket;

public class MemberListResponseHandler extends SimpleChannelInboundHandler<MemberListResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MemberListResponsePacket packet) throws Exception {
        System.out.println("群里成员有：" + packet.getMemberList());
    }
}
