package protocal.model;

import lombok.Data;

import java.util.List;

import static protocal.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupResponsePacket extends Packet {

    private String groupId;
    private boolean success;
    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE.getValue();
    }
}
