package TT4J.activeLink;

/**
 * Created by Pawel on 2015-09-29.
 */
public class ServerInfo {
    private final String hostName;
    private final int port;
    private final boolean encrypted;

    public ServerInfo(String hostName, int port, boolean encrypted) {
        this.hostName = hostName;
        this.port = port;
        this.encrypted = encrypted;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public int getPort() {
        return port;
    }

    public String getHostName() {
        return hostName;
    }
}
