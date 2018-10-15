package protocal;

public enum  SerializerAlgorithm {

    JSON((byte) 1);

    private byte value;

    SerializerAlgorithm(byte value){
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
