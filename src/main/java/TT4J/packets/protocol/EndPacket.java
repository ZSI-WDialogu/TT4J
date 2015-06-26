package TT4J.packets.protocol;

import TT4J.enums.APINetworkPacketType;
import TT4J.packets.APINetworkPacket;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class EndPacket extends APINetworkPacket {
    public EndPacket() {
        super(APINetworkPacketType.END);
    }
}
