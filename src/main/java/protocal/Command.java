package protocal;

public enum Command {
    LOGIN_REQUEST((byte) 1),
    LOGIN_RESPONSE((byte) 2),
    MESSAGE_REQUEST((byte) 3),
    MESSAGE_RESPONSE((byte) 4),
    CREATE_GROUP((byte) 5),
    CREATE_GROUP_RESPONSE((byte) 6),
    JOIN_GROUP_REQUEST((byte) 7),
    JOIN_GROUP_RESPONSE((byte) 8),
    QUIT_GROUP_REQUEST((byte) 9),
    QUIT_GROUP_RESPONSE((byte) 10),
    MEMBER_REQUEST((byte) 11),
    MEMBER_RESPONSE((byte) 12),
    GROUP_MSG_REQUEST((byte) 13),
    LOGOUT_REQUEST((byte) 14);

    private byte value;

    Command(byte value){
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

}
