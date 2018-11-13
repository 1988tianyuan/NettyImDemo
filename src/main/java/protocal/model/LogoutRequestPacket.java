package protocal.model;

import lombok.Data;

import static protocal.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {

    private String userId;

    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST.getValue();
    }
}
