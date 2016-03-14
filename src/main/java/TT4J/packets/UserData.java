package TT4J.packets;

import TT4J.enums.APINetworkPacketType;
import TT4J.enums.UserType;

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
    private String nick;

    public UserData() {
        super(APINetworkPacketType.USER_ACCOUNT);
    }
    public UserData(String username, String nick, String password, UserType usertype, String note, String initchan, int userrights){
        this();

        this.username = username;
        this.nick = nick;
        this.password = password;
        this.usertype = usertype;
        this.note = note;
        this.initchan = initchan;
        this.userrights = userrights;
    }

    // Copy constructor
    public UserData(String nick, String username, String password){
        this();
        this.nick = nick;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("username=\"%s\" password=\"%s\" usertype=%s note=\"%s\" channel=\"%s\" userrights=%s userdata=%s",
                username, password, usertype, note, initchan, userrights, userdata);
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
