package TT4J.packets;

import TT4J.enums.APINetworkPacketType;

/**
 * Created by stokowiec on 2015-07-16.
 */
public class RemoveChannelPacket extends APINetworkPacket {

    private int chanid;

    public RemoveChannelPacket() {
        super(APINetworkPacketType.REMOVE_CHANNEL);
    }

    public int getChanid() {
        return chanid;
    }

    public void setChanid(int chanid) {
        this.chanid = chanid;
    }

    @Override
    public String toString()
    {
        return String.format("[ID %s] channel has been removed", chanid);
    }
}
