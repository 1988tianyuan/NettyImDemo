import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

	public static void main(String[] args){
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();

		//1. 添加work线程组
		//2. 指定io模型为nio方式
		//3. 指定ChannelHandler，即具体的业务处理逻辑
		bootstrap.group(workerGroup)
				 .channel(NioSocketChannel.class)
				 .handler(new ChannelInitializer<NioSocketChannel>() {
					 @Override
					 protected void initChannel(NioSocketChannel ch) {

					 }
				 });

		//建立TCP连接是异步过程，设置回调方法查看是否连接成功
		bootstrap.connect("127.0.0.1", 8000).addListener(future -> {
			if(future.isSuccess()){
				logger.debug("连接建立成功！");
			}else {
				logger.debug("连接建立失败！");

			}
		});


	}
}
