package protocal.model;

import lombok.Data;
import protocal.Command;

@Data
public class LoginRequestPacket extends Packet{

    private String userId;
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST.getValue();
    }
}
