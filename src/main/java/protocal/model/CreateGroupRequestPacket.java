package protocal.model;

import lombok.Data;
import protocal.Command;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP.getValue();
    }
}
