package teamtalk.packets;

import teamtalk.enums.APINetworkPacketType;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class APINetworkPacket {

    private APINetworkPacketType type;

    public APINetworkPacket(APINetworkPacketType type){
        this.type = type;
    }

    public APINetworkPacketType getType() {
        return type;
    }
}
