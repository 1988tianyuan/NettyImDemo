package channelHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.PacketCodeC;
import protocal.model.LoginRequestPacket;
import protocal.model.LoginResponsePacket;
import protocal.model.Session;
import utils.LoginUtil;
import utils.SessionUtil;

import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private static final String VALID_MSG = "登录成功啦";
    private static final String INVALID_MSG = "账号或密码错误";

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket packet) throws Exception {
        Channel currentChannel = channelHandlerContext.channel();
        //记录登录状态
        String userId = UUID.randomUUID().toString().substring(0, 10);
        String userName = packet.getUserName();
        Session session = new Session(userId, userName);
        SessionUtil.bindSession(session, currentChannel);

        LoginResponsePacket lrpPacket = loginCheck(packet, channelHandlerContext);
        lrpPacket.setUserId(userId);

        currentChannel.writeAndFlush(lrpPacket);
        channelHandlerContext.fireChannelRead(packet);
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
            //标记为登录成功
            LoginUtil.markAsLogin(ctx.channel());
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
            return name.equals("liugeng") && password.equals("123456");
        }
        return false;
    }
}
