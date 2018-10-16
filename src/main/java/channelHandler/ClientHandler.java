package channelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocal.PacketCodeC;
import protocal.model.LoginRequestPacket;
import protocal.model.LoginResponsePacket;
import protocal.model.Packet;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter{

    private static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("客户端开始登录...");

        //生成登录信息
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserName("liugeng");
        packet.setPassword("123456");
        packet.setUserId(UUID.randomUUID().toString());

        //编码为ByteBuf并写入channel发送到客户端
        ByteBuf buf = PacketCodeC.encode(ctx.alloc().ioBuffer(), packet);
        ctx.channel().writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        Packet packet = PacketCodeC.decode(buf);
        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket lrpPacket = (LoginResponsePacket) packet;
            handleLoginRsp(lrpPacket);
        }
    }

    private void handleLoginRsp(LoginResponsePacket lrpPacket){
        boolean success = lrpPacket.isSuccess();
        if(success){
            logger.debug("登录成功");
        }else {
            logger.error("登录失败，原因是: " + lrpPacket.getReason());
        }
    }
}
