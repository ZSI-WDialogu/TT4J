package teamtalk.packets;

import teamtalk.enums.APINetworkPacketType;

/**
 * Created by Stokowiec on 2015-06-23.
 */
public class RemoveUserPacket  extends  APINetworkPacket{

    private int userid;
    private int chanid;

    public RemoveUserPacket() {
        super(APINetworkPacketType.REMOVE_USER);
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getChanid() {
        return chanid;
    }

    public void setChanid(int chanid) {
        this.chanid = chanid;
    }
}
