package teamtalk;

import com.sun.javaws.exceptions.InvalidArgumentException;
import teamtalk.enums.APINetworkPacketType;
import teamtalk.interfaces.APIConnection;
import teamtalk.packets.APINetworkPacket;
import teamtalk.packets.RawPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Created by stokowiec on 2015-06-18.
 */
public class TeamTalkConnection implements APIConnection {

    private String hostName;
    private int portNumber;
    private int cmdId;
    private boolean isConnected;

    private PrintWriter out;
    private BufferedReader in;

    private Event<APINetworkPacket> packetReceived;
    private PacketDeserializer deserializer;

    public TeamTalkConnection(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.cmdId = 0;
        this.packetReceived = new Event<>();
        this.deserializer = new PacketDeserializer();
    }

    @Override
    public boolean connect() {

        if (!isConnected) {
            try {

                Socket socket = new Socket(hostName, portNumber);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                isConnected = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return isConnected;
    }

    @Override
    public void disconnect() {
        if(!isConnected)
            return;

        if(out!=null)
            out.close();

        if(in!=null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean sendCommand(String command) throws NotYetConnectedException {
        if(!isConnected)
            throw new NotYetConnectedException();

        cmdId += 1;
        String signedCommand = String.format("%s id=%s", command, cmdId);

        out.println(signedCommand);
        boolean response = processReply(cmdId);
        return response;
    }

    private boolean processReply(int command_id) {
        String fromServer;
        try {

            int current_command_id = 0;         // Variable to keep track of the command ID which is currently being processed
            boolean success = false;            // Variable to keep track of whether the $cmdid parameter succeeded

            while ((fromServer = in.readLine()) != null){

                RawPacket rawPacket = new RawPacket(fromServer);
                APINetworkPacketType APINetworkPacketType = rawPacket.type();

                switch(APINetworkPacketType){
                    case BEGIN:
                        current_command_id =  Integer.parseInt(rawPacket.getValue("id"));
                        break;
                    case END:
                        if(command_id == Integer.parseInt(rawPacket.getValue("id")))
                            return success;
                        else
                            current_command_id = 0;
                        break;
                    case OK:
                        if(current_command_id==command_id)
                            success = true;
                        break;
                    default:
                        try {

                            APINetworkPacket packet = deserializer.deserialize(rawPacket);
                            packetReceived.invoke(packet);

                        } catch (InvalidArgumentException e) {
                            e.printStackTrace();
                        }
                }

                if(command_id == 0)
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public void onPacketReceived(Consumer<APINetworkPacket> consumer) {
        packetReceived.register(consumer);
    }
}
