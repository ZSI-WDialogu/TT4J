package teamtalk.packets;

import teamtalk.enums.APINetworkPacketType;
import teamtalk.enums.UserType;

import java.util.List;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class AcceptedPacket extends APINetworkPacket {

    private int userdata;
    private int userid;
    private int audiocodeclimit;
    private int userrights;

    private String username;
    private String nickname;
    private String note;
    private String initchan;
    private UserType usertype;
    private String ipaddr;
    private List<Integer> opchannels;


    public AcceptedPacket() {
        super(APINetworkPacketType.ACCEPTED);
    }

    public int getUserdata() {
        return userdata;
    }

    public void setUserdata(int userdata) {
        this.userdata = userdata;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getAudiocodeclimit() {
        return audiocodeclimit;
    }

    public void setAudiocodeclimit(int audiocodeclimit) {
        this.audiocodeclimit = audiocodeclimit;
    }

    public int getUserrights() {
        return userrights;
    }

    public void setUserrights(int userrights) {
        this.userrights = userrights;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInitchan() {
        return initchan;
    }

    public void setInitchan(String initchan) {
        this.initchan = initchan;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public List<Integer> getOpchannels() {
        return opchannels;
    }

    public void setOpchannels(List<Integer> opchannels) {
        this.opchannels = opchannels;
    }
}
