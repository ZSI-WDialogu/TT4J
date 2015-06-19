package teamtalk.packets;

import teamtalk.enums.APINetworkPacketType;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class ErrorPacket extends APINetworkPacket {

    private int number;
    private String message;

    public ErrorPacket() {
        super(APINetworkPacketType.ERROR);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
