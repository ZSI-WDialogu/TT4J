package teamtalk.packets;

import teamtalk.enums.APINetworkPacketType;
import teamtalk.enums.AudioCodec;

import java.util.List;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class AddChannelPacket extends APINetworkPacket{

    private String channel;
    private String oppassword;
    private String password;
    private String topic;
    private int chanid;
    private int parentid;
    private int maxusers;
    private int type;
    private int diskquota;
    private int isProtected;
    private AudioCodec audiocodec;
    private int[] audiocfg;
    private int[]operators;
    private int userdata;

    public AddChannelPacket() {
        super(APINetworkPacketType.ADD_CHANNEL);
    }

    public int getProtected() {
        return isProtected;
    }

    public void setProtected(int isProtected) {
        this.isProtected = isProtected;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOppassword() {
        return oppassword;
    }

    public void setOppassword(String oppassword) {
        this.oppassword = oppassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getChanid() {
        return chanid;
    }

    public void setChanid(int chanid) {
        this.chanid = chanid;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(int maxusers) {
        this.maxusers = maxusers;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDiskquota() {
        return diskquota;
    }

    public void setDiskquota(int diskquota) {
        this.diskquota = diskquota;
    }

    public AudioCodec getAudiocodec() {
        return audiocodec;
    }

    public void setAudiocodec(AudioCodec audiocodec) {
        this.audiocodec = audiocodec;
    }

    public int[]getAudiocfg() {
        return audiocfg;
    }

    public void setAudiocfg(int[]audiocfg) {
        this.audiocfg = audiocfg;
    }

    public int[]getOperators() {
        return operators;
    }

    public void setOperators(int[]operators) {
        this.operators = operators;
    }

    public int getUserdata() {
        return userdata;
    }

    public void setUserdata(int userdata) {
        this.userdata = userdata;
    }
}
