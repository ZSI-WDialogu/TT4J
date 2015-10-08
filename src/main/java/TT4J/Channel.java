package TT4J;

import TT4J.enums.AudioCodec;

/**
 * Created by stokowiec on 2015-06-18.
 */
public class Channel {

    private final byte type;
    private int channelID;
    private int parentID;
    private boolean isPermanent;
    private String name;
    private String password;
    private String topic;
    private AudioCodec audioCodec;

    public Channel(int parentID, byte type, String name, String password, String topic, AudioCodec audioCodec) {
        this.parentID = parentID;
        this.type = type;
        this.name = name;
        this.password = password;
        this.topic = topic;
        this.audioCodec = audioCodec;
    }

    @Override
    public String toString() {
        return String.format("name=\"%s\" password=\"%s\" parentid=%s type=%s topic=\"%s\" audiocodec=%s",
                name, password, parentID, type, topic, audioCodec);
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setIsPermanent(boolean isPermanent) {
        this.isPermanent = isPermanent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public AudioCodec getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(AudioCodec audioCodec) {
        this.audioCodec = audioCodec;
    }
}
