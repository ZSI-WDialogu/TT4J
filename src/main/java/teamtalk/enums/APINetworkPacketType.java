package teamtalk.enums;

/**
 * Created by Stokowiec on 2015-06-18.
 */
public enum APINetworkPacketType {
    HANDSHAKE("teamtalk"),
    BEGIN("begin"),
    END("end"),
    ACCEPTED("accepted"),
    SERVER_UPDATE("serverupdate"),
    ADD_CHANEL("addchannel"),
    LOGGED_IN("loggedin"),
    ADD_USER("adduser"),
    OK("ok"),
    ERROR("error"),
    USER_ACCOUNT("useraccount");

    private String text;

    APINetworkPacketType(String text) {
        this.text = text;
    }

    public static APINetworkPacketType fromString(String text) {
        if (text != null) {
            for (APINetworkPacketType b : APINetworkPacketType.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        return null;
    }
}
