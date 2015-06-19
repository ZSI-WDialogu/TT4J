package teamtalk.packets.protocol;

import teamtalk.enums.APINetworkPacketType;
import teamtalk.packets.APINetworkPacket;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class BeginPacket extends APINetworkPacket{
    public BeginPacket(){
        super(APINetworkPacketType.BEGIN);
    }
}
