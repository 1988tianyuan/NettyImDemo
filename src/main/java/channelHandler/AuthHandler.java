package channelHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import utils.LoginUtil;

/**
 * 身份认证
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(LoginUtil.hasLogin(ctx.channel())) {
            ctx.pipeline().remove(this);//身份认证成功，移除这个handler，pipeline的热拔插机制
            super.channelRead(ctx, msg);
        }else {
            ctx.channel().close();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("身份认证成功，移除这个handler");
        }else {
            System.out.println("身份认证失败，关闭连接");
        }
    }
}
