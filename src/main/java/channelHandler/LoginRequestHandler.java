package channelHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocal.PacketCodeC;
import protocal.model.LoginRequestPacket;
import protocal.model.LoginResponsePacket;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private static final String VALID_MSG = "登录成功啦";
    private static final String INVALID_MSG = "账号或密码错误";

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket packet) throws Exception {
        channelHandlerContext.channel().writeAndFlush(loginCheck(packet));
    }

    private LoginResponsePacket loginCheck(LoginRequestPacket lrqPacket){
        LoginResponsePacket lrpPacket = new LoginResponsePacket();
        if(valid(lrqPacket)){
            //登录校验成功
            lrpPacket.setSuccess(true);
            lrpPacket.setReason(VALID_MSG);
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
