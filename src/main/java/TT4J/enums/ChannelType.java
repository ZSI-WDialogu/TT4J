package TT4J.enums;

public class ChannelType {
    public static final byte CHANNEL_DEFAULT = 0;
    public static final byte CHANNEL_PERMANENT = 1;
    public static final byte CHANNEL_SOLO_TRANSMIT = 2;
    public static final byte CHANNEL_CLASSROOM = 4;
    public static final byte CHANNEL_OPERATOR_RECVONLY = 8;
    public static final byte CHANNEL_NO_VOICEACTIVATION = 16;
    public static final byte CHANNEL_NO_RECORDING = 32;

    public static byte getDefaults() {
        return CHANNEL_PERMANENT | CHANNEL_CLASSROOM;
    }
}
