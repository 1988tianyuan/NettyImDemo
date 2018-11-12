package protocal.model;

import lombok.Data;

import static protocal.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {

    private String msg;
    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE.getValue();
    }
}
