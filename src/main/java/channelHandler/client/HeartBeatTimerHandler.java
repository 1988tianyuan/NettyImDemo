package channelHandler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocal.model.HeartBeatRequestPacket;

import java.util.concurrent.TimeUnit;

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEART_BEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        heartBeatSchedule(ctx);
        super.channelInactive(ctx);
    }

    private void heartBeatSchedule(ChannelHandlerContext ctx) {
        ctx.executor().schedule(()-> {
            if(ctx.channel().isActive()) {
                ctx.channel().writeAndFlush(new HeartBeatRequestPacket());
                heartBeatSchedule(ctx);
            }
        }, HEART_BEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
