package channelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocal.PacketCodeC;
import protocal.model.LoginRequestPacket;
import protocal.model.LoginResponsePacket;
import utils.LoginUtil;

import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    private static Logger logger = LoggerFactory.getLogger(LoginResponseHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("客户端开始登录...");

        //生成登录信息
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserName("liugeng");
        packet.setPassword("123456");
        packet.setUserId(UUID.randomUUID().toString());

        ctx.channel().writeAndFlush(packet);
//        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        handleLoginRsp(loginResponsePacket, channelHandlerContext.channel());
    }

    private void handleLoginRsp(LoginResponsePacket lrpPacket, Channel channel){
        boolean success = lrpPacket.isSuccess();
        if(success){
            //标记为登录成功
            LoginUtil.markAsLogin(channel);
            logger.debug("登录成功");
        }else {
            logger.error("登录失败，原因是: " + lrpPacket.getReason());
        }
    }
}
