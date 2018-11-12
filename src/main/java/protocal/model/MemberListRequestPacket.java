package protocal.model;

import lombok.Data;
import protocal.Command;

import java.util.List;

@Data
public class MemberListRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.MEMBER_REQUEST.getValue();
    }
}
