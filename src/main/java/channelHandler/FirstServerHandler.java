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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] bytes = "我们已经连接上啦！".getBytes(Charset.forName("UTF-8"));
        buffer.writeBytes(bytes);
        logger.debug("有客户端连接过来，向客户端发送信息：" + buffer.toString(Charset.forName("UTF-8")));
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //服务器端收到数据
        ByteBuf buffer = (ByteBuf) msg;
        logger.debug("服务器端读到数据：" + buffer.toString(Charset.forName("UTF-8")));

        ByteBuf outBuffer = getByteBuf(ctx);
        logger.debug("服务器端写出数据：" + outBuffer.toString(Charset.forName("UTF-8")));
        //向服务器端写数据
        ctx.channel().writeAndFlush(outBuffer);

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf outBuffer = ctx.alloc().buffer();
        byte[] bytes = "好了我知道了".getBytes(Charset.forName("UTF-8"));
        outBuffer.writeBytes(bytes);
        return outBuffer;
    }
}
