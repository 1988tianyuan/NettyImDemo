package protocal.model;

import lombok.Data;
import protocal.Command;

import java.util.List;

@Data
public class MemberListResponsePacket extends Packet {

    private List<String> memberList;

    @Override
    public Byte getCommand() {
        return Command.MEMBER_RESPONSE.getValue();
    }
}
