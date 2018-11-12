package console;

import io.netty.channel.Channel;
import protocal.model.GroupMessageRequestPacket;
import protocal.model.Session;
import utils.Constants;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入群聊内容：");
        String msg = scanner.nextLine();
        Session session = channel.attr(Constants.SESSION).get();
        String groupId = session.getGroupId();
        if(groupId != null) {
            GroupMessageRequestPacket packet = new GroupMessageRequestPacket();
            packet.setFromUser(session.getUserName());
            packet.setMsg(msg);
            packet.setGroupId(groupId);
            channel.writeAndFlush(packet);
        }else {
            System.out.println("你不在一个群里！");
        }
    }
}
