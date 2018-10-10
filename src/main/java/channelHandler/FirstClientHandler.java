package channelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class FirstClientHandler extends ChannelInboundHandlerAdapter{
    private static Logger logger = LoggerFactory.getLogger(FirstClientHandler.class);

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
        //获取二进制抽象ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] bytes = "这是一行文字".getBytes(Charset.forName("UTF-8"));
        buffer.writeBytes(bytes);
        return buffer;
    }


}
