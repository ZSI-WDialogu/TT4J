package teamtalk.packets.protocol;

import teamtalk.enums.APINetworkPacketType;
import teamtalk.packets.APINetworkPacket;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class OKPacket extends APINetworkPacket {
    public OKPacket(){
        super(APINetworkPacketType.OK);
    }
}
