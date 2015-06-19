package teamtalk.interfaces;

import teamtalk.packets.APINetworkPacket;
import teamtalk.packets.RawPacket;
import java.nio.channels.NotYetConnectedException;
import java.util.function.Consumer;

/**
 * Created by stokowiec on 2015-06-18.
 */
public interface APIConnection {
    boolean connect();
    void disconnect();
    void onPacketReceived(Consumer<APINetworkPacket> consmer);
    boolean sendCommand(String command) throws NotYetConnectedException;
}
