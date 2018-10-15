package protocal.model;

import protocal.Command;

public class LoginRequestPacket extends Packet{

    private Integer userId;
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST.getValue();
    }
}
