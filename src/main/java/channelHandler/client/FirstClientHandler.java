package channelHandler.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocal.PacketCodeC;
import protocal.model.LoginRequestPacket;

import java.nio.charset.Charset;

public class FirstClientHandler extends ChannelInboundHandlerAdapter{
    private static Logger logger = LoggerFactory.getLogger(FirstClientHandler.class);
    private final PacketCodeC packetCodeC = new PacketCodeC();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //获取上下文中已经填充好数据的ByteBuf
        ByteBuf buffer = getByteBuf(ctx);
        //向服务器端写数据
        logger.debug("客户端写出数据：" + buffer.toString(Charset.forName("UTF-8")));
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //客户端收到数据
        ByteBuf buffer = (ByteBuf) msg;
        logger.debug("可读字节数：" + buffer.readableBytes());
        logger.debug("客户端读到数据：" + buffer.toString(Charset.forName("UTF-8")));
    }

    //向上下文中的ByteBuf填充数据
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId("111");
        packet.setUserName("刘耕");
        packet.setPassword("123456");
        ByteBuf buf = packetCodeC.encode(ctx.alloc().ioBuffer(), packet);
        return buf;
    }


}
