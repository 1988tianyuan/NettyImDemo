package console;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import protocal.model.LoginRequestPacket;

import java.util.Scanner;

@Slf4j
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket lrqPacket = new LoginRequestPacket();
        log.debug("客户端开始登录...");
        System.out.println("请输入用户名：");
        String userName = scanner.nextLine();
        System.out.println("请输入密码：");
        String pwd = scanner.nextLine();
        lrqPacket.setUserName(userName);
        lrqPacket.setPassword(pwd);
        channel.writeAndFlush(lrqPacket);
    }
}
