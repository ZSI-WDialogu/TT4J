package TT4J;

import TT4J.enums.APINetworkPacketType;
import TT4J.interfaces.APIConnection;
import TT4J.packets.APINetworkPacket;
import TT4J.packets.RawPacket;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.NotYetConnectedException;
import java.util.function.Consumer;

/**
 *  This class servers as a connection layer with TeamTalk server.
 *  The TeamTalk protocol is not a request/reply protocol.
 *
 *  The server can send commands which are not replies
 *  issued by the client (you). E.g. once you have logged in you can
 *  get a command from the server saying that a user has joined a
 *  channel which is not related to any command you have issued. To
 *  know if a command is a reply to a command you have issued you need
 *  to put in the parameter "id=123" where 123 is the command ID of the
 *  command you want to trace. The server will then encapsulate its
 *  reply to your command in 'begin' - 'end' replies.
 *
 *  Although it may sound complicated it's very simple. Say you have
 *  logged on to a server and want to list all user accounts. You do
 *  this by issuing the command "listaccounts id=123". The server will
 *  send repond like this:
 *
 *  begin id=123
 *  useraccount username="ben" password="pass123" usertype=1
 *  useraccount username="jill" password="pppjjj" usertype=2
 *  ok
 *  end id=123
 *
 *  When you get the 'end' command it means there will be no more
 *  commands issued by the server related to the command with the
 *  specified 'id'. The 'ok' command reply means that the command
 *  executed successfully. Had it not the command 'error' would have
 *  been returned like in the following example:
 *
 *  begin id=123
 *  error number=2006 message="Command not authorized"
 *  end id=123
 *
 *  The 'id' parameter can be omitted which is sometimes convenient but
 *  if you want to know which commands issued by the server are related
 *  to a command issued by you you need to include the 'id' parameter.

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


    /**
     * Initialise connection to TeamTalk server
     * @return true if connection is successful, false otherwise
     */
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

    /**
     * Terminates connection to TeamTalk server
     */
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

    /**
     * Send string command to TeamTalk server
     * @param command to be send
     * @return true, if command has been successfully processed, false otherwise
     * @throws NotYetConnectedException
     */
    @Override
    public boolean sendCommand(String command) throws NotYetConnectedException {
        if(!isConnected)
            throw new NotYetConnectedException();

        cmdId += 1;
        String signedCommand = String.format("%s id=%s", command, cmdId);

        out.println(signedCommand);
        return processReply(cmdId);
    }

    /**
     * After message has been send it ought to be processed with accordance to TeamTalk protocol.
     * This method keep track of incoming packets with their respective ids and tries to deserialize packets
     * @param command_id id of the command send to the server
     * @return true, if command has been successfully processed, false otherwise
     */
    private boolean processReply(int command_id) {
        String fromServer;
        try {

            int current_command_id = 0;         // Variable to keep track of the command ID which is currently being processed

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

    /**
     * Checks if client has successfully connected to server
     * @return true, if client is connected, false otherwise
     */
    public boolean getIsConnected() {
        return isConnected;
    }

    /**
     * Event to be subscribed. When network packet is successfully deserialized this event is invoked.
     * @param consumer
     */
    public void onPacketReceived(Consumer<APINetworkPacket> consumer) {
        packetReceived.register(consumer);
    }
}
