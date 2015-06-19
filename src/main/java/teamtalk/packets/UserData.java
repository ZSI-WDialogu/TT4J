package teamtalk.packets;

import teamtalk.enums.APINetworkPacketType;
import teamtalk.enums.UserType;

import java.util.List;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class UserData extends APINetworkPacket {

    private String username;
    private String password;
    private String initchan;
    private String note;
    private int userrights;
    private int audiocodeclimit;
    private int userdata;
    private UserType usertype;
    private List<Integer> opchannels;

    public UserData() {
        super(APINetworkPacketType.USER_ACCOUNT);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInitchan() {
        return initchan;
    }

    public void setInitchan(String initchan) {
        this.initchan = initchan;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUserrights() {
        return userrights;
    }

    public void setUserrights(int userrights) {
        this.userrights = userrights;
    }

    public int getAudiocodeclimit() {
        return audiocodeclimit;
    }

    public void setAudiocodeclimit(int audiocodeclimit) {
        this.audiocodeclimit = audiocodeclimit;
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

    public List<Integer> getOpchannels() {
        return opchannels;
    }

    public void setOpchannels(List<Integer> opchannels) {
        this.opchannels = opchannels;
    }
}
