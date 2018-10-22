package channelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocal.PacketCodeC;
import protocal.model.*;

import java.nio.charset.Charset;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final String VALID_MSG = "登录成功啦";
    private static final String INVALID_MSG = "账号或密码错误";


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel();
        ByteBuf buf = (ByteBuf) msg;
        Packet packet = PacketCodeC.decode(buf);
        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket lrqPacket = (LoginRequestPacket) packet;
            LoginResponsePacket lrpPacket = new LoginResponsePacket();
            ByteBuf outerBuf = ctx.alloc().ioBuffer();
            if(valid(lrqPacket)){
                //登录校验成功
                lrpPacket.setSuccess(true);
                lrpPacket.setReason(VALID_MSG);
                PacketCodeC.encode(outerBuf, lrpPacket);
            }else {
                //登录校验失败
                lrpPacket.setSuccess(false);
                lrpPacket.setReason(INVALID_MSG);
                PacketCodeC.encode(outerBuf, lrpPacket);
            }
            ctx.channel().writeAndFlush(outerBuf);
        }else if (packet instanceof MessageRequestPacket){
            //接收到客户端对话消息
            MessageRequestPacket mqPacket = (MessageRequestPacket) packet;
            String message = mqPacket.getMessage();
            System.out.println("客户端说：" + message);
            MessageResponsePacket mpPacket = new MessageResponsePacket();
            mpPacket.setMessage("好了好了我收到了，哎...");
            ByteBuf responseBuf = PacketCodeC.encode(ctx.alloc().ioBuffer(), mpPacket);
            ctx.channel().writeAndFlush(responseBuf);
            System.out.println("服务端说："  + mpPacket.getMessage());
        }

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
