package channelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class FirstServerHandler extends ChannelInboundHandlerAdapter{
    private static Logger logger = LoggerFactory.getLogger(FirstServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //服务器端收到数据
        ByteBuf buffer = (ByteBuf) msg;
        logger.debug("服务器端读到数据：" + buffer.toString(Charset.forName("UTF-8")));

        ByteBuf outBuffer = getByteBuf(ctx);
        //向服务器端写数据
        ctx.channel().writeAndFlush(outBuffer);
        logger.debug("服务器端写出数据");
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf outBuffer = ctx.alloc().buffer();
        byte[] bytes = "好了我知道了".getBytes(Charset.forName("UTF-8"));
        outBuffer.writeBytes(bytes);
        return outBuffer;
    }
}
