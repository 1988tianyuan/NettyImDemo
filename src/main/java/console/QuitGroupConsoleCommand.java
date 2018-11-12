package console;

import io.netty.channel.Channel;
import protocal.model.QuitGroupRequestPacket;
import protocal.model.QuitGroupResponsePacket;
import utils.Constants;

import java.util.Scanner;

public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();
        quitGroupRequestPacket.setGroupId(channel.attr(Constants.SESSION).get().getGroupId());
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
