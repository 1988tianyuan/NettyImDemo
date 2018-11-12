package console;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import protocal.model.MessageRequestPacket;

import java.util.Scanner;

@Slf4j
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        log.debug("写消息发送至服务器");
        System.out.println("请输入对方id： ");
        String toUserId = scanner.nextLine();
        System.out.println("请输要发送的消息：");
        String line = scanner.nextLine();
        MessageRequestPacket mqPacket = new MessageRequestPacket();
        mqPacket.setToUserId(toUserId);
        mqPacket.setMessage(line);
        channel.writeAndFlush(mqPacket);
    }
}
