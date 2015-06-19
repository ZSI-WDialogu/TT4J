package teamtalk.packets;

import teamtalk.enums.APINetworkPacketType;
import teamtalk.enums.UserType;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class LoggedInPacket extends APINetworkPacket {

    private int userid;
    private int userdata;
    private int statusmode;
    private int sublocal;
    private int subpeer;

    private String username;
    private String nickname;
    private String statusmsg;
    private String ipaddr;
    private String version;
    private String packetprotocol;

    private UserType usertype;

    public LoggedInPacket() {
        super(APINetworkPacketType.LOGGED_IN);
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserdata() {
        return userdata;
    }

    public void setUserdata(int userdata) {
        this.userdata = userdata;
    }

    public int getStatusmode() {
        return statusmode;
    }

    public void setStatusmode(int statusmode) {
        this.statusmode = statusmode;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatusmsg() {
        return statusmsg;
    }

    public void setStatusmsg(String statusmsg) {
        this.statusmsg = statusmsg;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPacketprotocol() {
        return packetprotocol;
    }

    public void setPacketprotocol(String packetprotocol) {
        this.packetprotocol = packetprotocol;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }
}
