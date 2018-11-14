package protocal.model;

import lombok.Data;

import static protocal.Command.HEART_BEAT_REQUEST;

@Data
public class HeartBeatRequestPacket extends Packet{

    private String heartBeatMsg = "hello";

    @Override
    public Byte getCommand() {
        return HEART_BEAT_REQUEST.getValue();
    }
}
