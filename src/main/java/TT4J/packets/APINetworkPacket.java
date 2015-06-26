package TT4J.packets;

import TT4J.enums.APINetworkPacketType;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class APINetworkPacket {

    private APINetworkPacketType packetType;

    public APINetworkPacket(APINetworkPacketType type){
        this.packetType = type;
    }

    public APINetworkPacketType getPacketType() {
        return packetType;
    }
}
