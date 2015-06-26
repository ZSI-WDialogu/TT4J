package TT4J.packets.protocol;

import TT4J.enums.APINetworkPacketType;
import TT4J.packets.APINetworkPacket;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class BeginPacket extends APINetworkPacket{
    public BeginPacket(){
        super(APINetworkPacketType.BEGIN);
    }
}
