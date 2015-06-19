package teamtalk.packets;

import teamtalk.enums.APINetworkPacketType;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class ServerUpdatePacket extends  APINetworkPacket {

    // Server properties
    private String servername;
    private int maxusers;
    private String motd;
    private String motdraw;
    private int autosave;

    private int tcpport;
    private int udpport;
    private int usertimeout;

    // Server bandwidth limitation
    private int voicetxlimit;
    private int videotxlimit;
    private int mediafiletxlimit;
    private int desktoptxlimit;
    private int totaltxlimit;

    // Server abuse
    private int maxloginattempts;
    private int maxiplogins;

    // Server information
    private String version;

    public ServerUpdatePacket() {
        super(APINetworkPacketType.SERVER_UPDATE);
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public int getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(int maxusers) {
        this.maxusers = maxusers;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public String getMotdraw() {
        return motdraw;
    }

    public void setMotdraw(String motdraw) {
        this.motdraw = motdraw;
    }

    public int getAutosave() {
        return autosave;
    }

    public void setAutosave(int autosave) {
        this.autosave = autosave;
    }

    public int getTcpport() {
        return tcpport;
    }

    public void setTcpport(int tcpport) {
        this.tcpport = tcpport;
    }

    public int getUdpport() {
        return udpport;
    }

    public void setUdpport(int udpport) {
        this.udpport = udpport;
    }

    public int getUsertimeout() {
        return usertimeout;
    }

    public void setUsertimeout(int usertimeout) {
        this.usertimeout = usertimeout;
    }

    public int getVoicetxlimit() {
        return voicetxlimit;
    }

    public void setVoicetxlimit(int voicetxlimit) {
        this.voicetxlimit = voicetxlimit;
    }

    public int getVideotxlimit() {
        return videotxlimit;
    }

    public void setVideotxlimit(int videotxlimit) {
        this.videotxlimit = videotxlimit;
    }

    public int getMediafiletxlimit() {
        return mediafiletxlimit;
    }

    public void setMediafiletxlimit(int mediafiletxlimit) {
        this.mediafiletxlimit = mediafiletxlimit;
    }

    public int getDesktoptxlimit() {
        return desktoptxlimit;
    }

    public void setDesktoptxlimit(int desktoptxlimit) {
        this.desktoptxlimit = desktoptxlimit;
    }

    public int getTotaltxlimit() {
        return totaltxlimit;
    }

    public void setTotaltxlimit(int totaltxlimit) {
        this.totaltxlimit = totaltxlimit;
    }

    public int getMaxloginattempts() {
        return maxloginattempts;
    }

    public void setMaxloginattempts(int maxloginattempts) {
        this.maxloginattempts = maxloginattempts;
    }

    public int getMaxiplogins() {
        return maxiplogins;
    }

    public void setMaxiplogins(int maxiplogins) {
        this.maxiplogins = maxiplogins;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
