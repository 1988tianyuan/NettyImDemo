package console;

import io.netty.channel.Channel;
import protocal.model.MemberListRequestPacket;
import protocal.model.Session;
import utils.Constants;

import java.util.Scanner;

public class MemberListConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        MemberListRequestPacket packet = new MemberListRequestPacket();
        String groupId = channel.attr(Constants.SESSION).get().getGroupId();
        if(groupId != null) {
            packet.setGroupId(groupId);
            channel.writeAndFlush(packet);
        }else {
            System.out.println("你不在一个群里！");
        }
    }
}
