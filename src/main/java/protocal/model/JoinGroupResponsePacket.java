package protocal.model;

import lombok.Data;

import static protocal.Command.JOIN_GROUP_RESPONSE;

@Data
public class JoinGroupResponsePacket extends Packet {

    private String msg;
    private String groupId;
    private boolean success;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE.getValue();
    }
}
