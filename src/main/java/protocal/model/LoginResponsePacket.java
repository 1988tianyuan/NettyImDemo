package protocal.model;

import lombok.Data;
import protocal.Command;

@Data
public class LoginResponsePacket extends Packet {

    private Session session;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE.getValue();
    }
}
