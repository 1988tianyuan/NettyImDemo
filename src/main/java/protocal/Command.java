package protocal;

public enum Command {
    LOGIN_REQUEST((byte) 1),
    LOGIN_RESPONSE((byte) 2);

    private byte value;

    Command(byte value){
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

}
