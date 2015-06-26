package TT4J.packets;

import TT4J.enums.APINetworkPacketType;
import TT4J.enums.UserType;

/**
 * Created by stokowiec on 2015-06-19.
 */

// Packet containing information that given user is sitting on a given channel
public class AddUserPacket extends APINetworkPacket {

    private int userid;
    private int chanid;
    private int statusmode;
    private int packetprotocol;
    private int sublocal;
    private int subpeer;
    private int userdata;

    private UserType usertype;

    private String nickname;
    private String username;
    private String ipaddr;
    private String statusmsg;
    private String version;

    public AddUserPacket() {
        super(APINetworkPacketType.ADD_USER);
    }

    @Override
    public String toString(){
        return String.format("[ID %s] user name: %s, nick name %s, type: %s, channel: %s", userid, username, nickname, usertype, chanid);
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

    public int getStatusmode() {
        return statusmode;
    }

    public void setStatusmode(int statusmode) {
        this.statusmode = statusmode;
    }

    public int getPacketprotocol() {
        return packetprotocol;
    }

    public void setPacketprotocol(int packetprotocol) {
        this.packetprotocol = packetprotocol;
    }

    public int getSublocal() {
        return sublocal;
    }

    public void setSublocal(int sublocal) {
        this.sublocal = sublocal;
    }

    public int getSubpeer() {
        return subpeer;
    }

    public void setSubpeer(int subpeer) {
        this.subpeer = subpeer;
    }

    public int getUserdata() {
        return userdata;
    }

    public void setUserdata(int userdata) {
        this.userdata = userdata;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getStatusmsg() {
        return statusmsg;
    }

    public void setStatusmsg(String statusmsg) {
        this.statusmsg = statusmsg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
