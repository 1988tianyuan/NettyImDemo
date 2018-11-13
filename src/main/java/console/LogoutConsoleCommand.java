package console;

import io.netty.channel.Channel;
import protocal.model.LogoutRequestPacket;
import protocal.model.Session;
import utils.Constants;
import utils.LoginUtil;
import utils.SessionUtil;

import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        Session session = channel.attr(Constants.SESSION).get();
        if(session != null) {
            logoutRequestPacket.setUserId(session.getUserId());
            LoginUtil.markAsLogout(channel);
        }
        channel.writeAndFlush(logoutRequestPacket);
    }
}
