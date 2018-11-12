package protocal.model;

import lombok.Data;
import protocal.Command;

@Data
public class GroupMessageRequestPacket extends Packet {

    private String groupId;
    private String msg;
    private String fromUser;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MSG_REQUEST.getValue();
    }
}
