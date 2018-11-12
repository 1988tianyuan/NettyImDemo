package channelHandler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.model.QuitGroupResponsePacket;
import protocal.model.Session;
import utils.Constants;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket packet) throws Exception {
        System.out.println(packet.getMsg());
        ctx.channel().attr(Constants.SESSION).get().setGroupId(null);
    }
}
