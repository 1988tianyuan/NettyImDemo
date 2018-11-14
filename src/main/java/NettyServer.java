import channelHandler.*;
import channelHandler.client.PacketCodecHandler;
import channelHandler.server.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class NettyServer {

    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public static void main(String[] args){
        //BossSelector，用于监听端口，accept新连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //WorkerSelector，用于执行具体的io操作
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //1. 添加boss和work线程组
        //2. 指定io模型为nio方式
        //3. 指定server端启动时的初始化handler
        //4. 指定ChannelHandler，即具体的业务处理逻辑
        //5. 给NioServerSocketChannel指定attributes，后续可以通过channel.attr()取出这个属性
        //6. 给NioSocketChannel指定attributes
        //7. 给NioSocketChannel指定一些选项，比如是否开启TCP心跳机制或者Nagle算法等
        //8. 给NioServerSocketChannel指定一些选项，比如设置完成三次握手的请求的缓存队列大小
        serverBootstrap.group(bossGroup, workerGroup)
                       .channel(NioServerSocketChannel.class)
                       .handler(new ChannelInitializer<NioServerSocketChannel>() {
                           @Override
                           protected void initChannel(NioServerSocketChannel channel){
                                logger.debug("服务端启动中...");
                           }
                       })
                       .childHandler(new ChannelInitializer<NioSocketChannel>() {
                           protected void initChannel(NioSocketChannel nioSocketChannel){
                               //（责任链模式）pipeline添加逻辑处理器，当接收到客户端数据时按顺序执行回调
                                nioSocketChannel.pipeline()
                                        .addLast(new IMIdleStateHandler())
                                        .addLast(new Spliter())
//                                        .addLast(new LifeCycleTestHandler())
                                        .addLast(PacketCodecHandler.INSTANCE)
                                        .addLast(LoginRequestHandler.INSTANCE)
                                        .addLast(new HeartBeatRequestHandler())
                                        .addLast(AuthHandler.INSTANCE)
                                        .addLast(IMServerHandler.INSTANCE)
                                        .addLast(new ExceptionCaughtHandler());
                           }
                       })
                       .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                       .childAttr(AttributeKey.newInstance("clientKey"), "clientValue")
                       .childOption(ChannelOption.SO_KEEPALIVE, true)
                       .childOption(ChannelOption.TCP_NODELAY, true)
                       .option(ChannelOption.SO_BACKLOG, 1024);

        //绑定端口是一个异步过程，设置回调方法查看是否绑定成功
        serverBootstrap.bind(8000).addListener(future -> {
            if(future.isSuccess()){
                logger.debug("8000端口绑定成功！");
            }else {
                logger.debug("8000端口绑定失败！");
            }
        });
    }
}
