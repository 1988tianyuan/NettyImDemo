package protocal.model;

import lombok.Data;
import protocal.Command;

@Data
public class MessageRequestPacket extends Packet {

    private String toUserId;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST.getValue();
    }
}
