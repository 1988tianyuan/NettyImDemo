package channelHandler.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.model.LogoutRequestPacket;
import protocal.model.MessageResponsePacket;
import protocal.model.Session;
import utils.Constants;
import utils.SessionUtil;

@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket>{

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();
    private LogoutRequestHandler() {}


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket packet) throws Exception {
        SessionUtil.unbindSession(ctx.channel());
        ctx.channel().attr(Constants.LOGIN).set(false);
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("退出登录");
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
