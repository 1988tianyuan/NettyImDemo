package channelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class LifeCycleTestHandler extends ChannelInboundHandlerAdapter{

    private static final AtomicInteger i = new AtomicInteger();
    private static Timer timer = new Timer();
    private static volatile boolean flag = false;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel绑定到线程（NioEventLoop）：channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel取消绑定（NioEventLoop）：channelRegistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel准备就绪：channelActive");
        i.incrementAndGet();
        if(!flag) {
//            synchronized (LifeCycleTestHandler.class) {
                //每隔3秒打印一次当前连接数
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName() + "： 当前连接数是： " + i);
                    }
                }, 3000, 3000);
                flag = true;
//            }
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel关闭：channelInactive");
        i.decrementAndGet();
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel有数据可读：channelRead");
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("本地数据大小是：" + buf.readableBytes());
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel数据读取完成：channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("添加handler：handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("移除handler：handlerRemoved");
        super.handlerRemoved(ctx);
    }
}
