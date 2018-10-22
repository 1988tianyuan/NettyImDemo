package protocal.model;

import lombok.Data;
import protocal.Command;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE.getValue();
    }
}
