package console;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;
    private static final String COMMAND_SEND_TO_USER = "sendToUser";
    private static final String COMMAND_LOGOUT = "logout";
    private static final String COMMAND_CREATE_GROUP = "createGroup";
    private static final String COMMAND_JOIN_GROUP = "joinGroup";
    private static final String COMMAND_QUIT_GROUP = "quitGroup";
    private static final String MEMBER_LIST = "memberList";
    private static final String SEND_TO_GROUP = "sendToGroup";

    public ConsoleCommandManager() {
        this.consoleCommandMap = new HashMap<>();
        consoleCommandMap.put(COMMAND_SEND_TO_USER, new SendToUserConsoleCommand());
        consoleCommandMap.put(COMMAND_CREATE_GROUP, new CreateGroupConsoleCommand());
        consoleCommandMap.put(COMMAND_LOGOUT, new LogoutConsoleCommand());
        consoleCommandMap.put(COMMAND_JOIN_GROUP, new JoinGroupConsoleCommand());
        consoleCommandMap.put(COMMAND_QUIT_GROUP, new QuitGroupConsoleCommand());
        consoleCommandMap.put(MEMBER_LIST, new MemberListConsoleCommand());
        consoleCommandMap.put(SEND_TO_GROUP, new SendToGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        //最开始的第一行命令
        String command = scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if(consoleCommand == null) {
            System.out.println(String.format("无法识别该指令: [%s]，请重新输入！", command));
        }else {
            consoleCommand.exec(scanner, channel);
        }
    }
}
