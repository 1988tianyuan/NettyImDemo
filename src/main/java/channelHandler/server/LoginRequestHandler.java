package channelHandler.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.PacketCodeC;
import protocal.model.LoginRequestPacket;
import protocal.model.LoginResponsePacket;
import protocal.model.Session;
import utils.LoginUtil;
import utils.SessionUtil;

import java.util.UUID;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private static final String VALID_MSG = "登录成功啦";
    private static final String INVALID_MSG = "账号或密码错误";

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
    private LoginRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket packet) throws Exception {
        Channel currentChannel = channelHandlerContext.channel();


        LoginResponsePacket lrpPacket = loginCheck(packet, channelHandlerContext);

        currentChannel.writeAndFlush(lrpPacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //清楚登录状态
        SessionUtil.unbindSession(ctx.channel());
        super.channelInactive(ctx);
    }

    private LoginResponsePacket loginCheck(LoginRequestPacket lrqPacket, ChannelHandlerContext ctx){
        LoginResponsePacket lrpPacket = new LoginResponsePacket();
        if(valid(lrqPacket)){
            //登录校验成功
            lrpPacket.setSuccess(true);
            lrpPacket.setReason(VALID_MSG);
            //记录登录状态
            String userId = UUID.randomUUID().toString().substring(0, 10);
            String userName = lrqPacket.getUserName();
            Session session = new Session();
            session.setUserName(userName);
            session.setUserId(userId);
            SessionUtil.bindSession(session, ctx.channel());
            lrpPacket.setSession(session);
            //标记为登录成功
            LoginUtil.markAsLogin(ctx.channel(), session);
        }else {
            //登录校验失败
            lrpPacket.setSuccess(false);
            lrpPacket.setReason(INVALID_MSG);
        }
        return lrpPacket;
    }

    private boolean valid(LoginRequestPacket packet){
        if(packet != null){
            String name = packet.getUserName();
            String password = packet.getPassword();
            return !name.isEmpty() && !password.isEmpty();
        }
        return false;
    }
}
