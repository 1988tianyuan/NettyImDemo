package channelHandler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IMIdleStateHandler extends IdleStateHandler{

    private static final int READ_TIME_OUT = 15;

    public IMIdleStateHandler() {
        super(READ_TIME_OUT, 0, 0);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.error(READ_TIME_OUT + "秒之内无心跳，断开连接: " + ctx.channel().id());
        ctx.channel().close();
    }
}
