package TT4J.packets;

import TT4J.enums.APINetworkPacketType;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class HandshakePacket extends APINetworkPacket {

    private int maxiplogins;
    private int maxusers;
    private int userid;
    private int usertimeout;

    private String protocol;
    private String servername;

    public HandshakePacket() {
        super(APINetworkPacketType.HANDSHAKE);
    }

    public int getMaxiplogins() {
        return maxiplogins;
    }

    public void setMaxiplogins(int maxiplogins) {
        this.maxiplogins = maxiplogins;
    }

    public int getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(int maxusers) {
        this.maxusers = maxusers;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUsertimeout() {
        return usertimeout;
    }

    public void setUsertimeout(int usertimeout) {
        this.usertimeout = usertimeout;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }
}
