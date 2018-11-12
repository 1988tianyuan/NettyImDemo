package channelHandler.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocal.model.LoginResponsePacket;
import protocal.model.Session;
import utils.LoginUtil;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    private static Logger logger = LoggerFactory.getLogger(LoginResponseHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        handleLoginRsp(loginResponsePacket, channelHandlerContext.channel());
    }

    private void handleLoginRsp(LoginResponsePacket lrpPacket, Channel channel){
        boolean success = lrpPacket.isSuccess();
        if(success){
            Session session = lrpPacket.getSession();
            System.out.println("登录成功, 你的id是： " + session.getUserId());
            //标记为登录成功
            LoginUtil.markAsLogin(channel, session);
        }else {
            logger.error("登录失败，原因是: " + lrpPacket.getReason());
        }
    }
}
