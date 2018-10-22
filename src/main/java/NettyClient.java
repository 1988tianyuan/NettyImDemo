import channelHandler.ClientHandler;
import channelHandler.FirstClientHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import protocal.PacketCodeC;
import protocal.model.MessageRequestPacket;
import utils.LoginUtil;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
	private static Logger logger = LoggerFactory.getLogger(NettyClient.class);
	private static final int MAX_RETRY = 5;

	public static void main(String[] args){
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();

		//1. 添加work线程组
		//2. 指定io模型为nio方式
		//3. 指定ChannelHandler，即具体的业务处理逻辑
		//4. 给NioSocketChannel添加attributes
		//5. 给NioSocketChannel指定一些选项，比如是否开启心跳以及设置连接超时时间，以及Nagle算法
		bootstrap.group(workerGroup)
				 .channel(NioSocketChannel.class)
				 .handler(new ChannelInitializer<NioSocketChannel>() {
					 @Override
					 protected void initChannel(NioSocketChannel nioSocketChannel) {
					 	//添加ClientHandler，连接上后向服务器端传输数据
						 nioSocketChannel.pipeline().addLast(new ClientHandler());
					 }
				 }).attr(AttributeKey.newInstance("attrName"), "attrValue")
				 .option(ChannelOption.SO_KEEPALIVE, true)
				 .option(ChannelOption.TCP_NODELAY, true)
				 .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);

		connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
	}

	//建立TCP连接是异步过程，设置回调方法查看是否连接成功
	//失败重连，使用BootstrapConfig调取workerGroup进行延时任务进行重连
	private static void connect(Bootstrap bootstrap, String host, int port, int retry){
		bootstrap.connect(host, port).addListener(future -> {
			if(future.isSuccess()){
				logger.debug("连接建立成功！");
				//连接成功后获取channel，启动控制台线程，和服务端对话
				Channel channel = ((ChannelFuture) future).channel();
				startConsoleThread(channel);
			}else if(retry == 0){
				logger.debug("重试次数已经用尽，放弃连接！");
			}else {
				//失败重连
				int order = MAX_RETRY - retry + 1;
				logger.debug("连接失败，第" + order + "次重连");
				int delay = 1 << order;
				bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry-1), delay, TimeUnit.SECONDS);
			}
		});
	}

	private static void startConsoleThread(Channel channel) {
		new Thread(
			()-> {
				while (!Thread.interrupted()){
					//判断是否登录
					if(LoginUtil.hasLogin(channel)){
						logger.debug("写消息发送至服务器");
						Scanner sc = new Scanner(System.in);
						String line = sc.nextLine();
						MessageRequestPacket mqPacket = new MessageRequestPacket();
						mqPacket.setMessage(line);
						ByteBuf buf = PacketCodeC.encode(channel.alloc().ioBuffer(), mqPacket);
						channel.writeAndFlush(buf);
					}else {
						logger.debug("未登录");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		).start();

	}
}













