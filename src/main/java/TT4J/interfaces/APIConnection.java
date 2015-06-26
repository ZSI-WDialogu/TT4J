package TT4J.interfaces;

import TT4J.packets.APINetworkPacket;

import java.nio.channels.NotYetConnectedException;
import java.util.function.Consumer;

/**
 * Created by stokowiec on 2015-06-18.
 */
public interface APIConnection {
    boolean connect();
    void disconnect();
    void onPacketReceived(Consumer<APINetworkPacket> consumer);
    boolean sendCommand(String command) throws NotYetConnectedException;
}
