package console;

import io.netty.channel.Channel;
import protocal.model.CreateGroupRequestPacket;
import protocal.model.Session;
import utils.Constants;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLIT = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        Session session = channel.attr(Constants.SESSION).get();
        String localUserId;
        if(session != null) {
            localUserId = session.getUserId();
        }else {
            System.out.println("无效登录，请重新登录！");
            channel.close();
            return;
        }
        System.out.println("[拉人群聊] 请输入需要拉入群聊的对方用户id，用逗号分割");
        String ids = scanner.next();
        ids += USER_ID_SPLIT + localUserId;
        createGroupRequestPacket.setUserIdList(Arrays.asList(ids.split(USER_ID_SPLIT)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
