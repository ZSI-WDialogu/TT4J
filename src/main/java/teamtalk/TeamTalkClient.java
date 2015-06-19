package teamtalk;

import teamtalk.enums.APINetworkPacketType;
import teamtalk.enums.UserType;
import teamtalk.interfaces.APIConnection;
import teamtalk.packets.APINetworkPacket;
import teamtalk.packets.UserData;

import java.util.*;
import java.util.function.Consumer;


public class TeamTalkClient {
    // Server info
    private String serverName;
    private int maxUsers;
    private int maxiplogins;
    private int userTimeOut=60;
    private String protocol;

    private List<UserData> users;
    private List<Object> channels;
    private APIConnection connection;

    private Event<UserData> onUserAdded = new Event<>();
    private Map<APINetworkPacketType, Consumer<APINetworkPacket>> packetHandlers;

    public TeamTalkClient(APIConnection connection){
        this.connection = connection;
        this.connection.onPacketReceived(this::handlePacket);
        this.users = new ArrayList<>();
        this.onUserAdded = new Event<>();
        initialiseHandlers();
    }

    // CONNECTION RELATED METHODS
    public boolean connect(){
        return connection.connect();
    }
    public void close() {
        connection.disconnect();
    }

    // PACKET HANDLING
    private void initialiseHandlers() {
        packetHandlers = new HashMap<>();
        packetHandlers.put(APINetworkPacketType.USER_ACCOUNT, this::handleUserDataPacket);
    }
    private void handlePacket(APINetworkPacket packet){
        Consumer<APINetworkPacket> handler = packetHandlers.getOrDefault(packet.getType(), null);
        if(handler!=null){
            handler.accept(packet);
        }
    }
    private void handleUserDataPacket(APINetworkPacket packet){
        UserData data = (UserData) packet;
        if(data != null && !users.contains(packet)) {
            users.add(data);
            onUserAdded.invoke(data);
        }
    }

    // USER COMMAND REGIONS
    public boolean login(String nick, String username, String password){
         return connection.sendCommand(String.format("login username=\"%s\" password=\"%s\" protocol=\"5.0\" nickname=\"%s\"", username, password, nick));
    }

    public boolean makeChannel(Channel channel){
        return connection.sendCommand(String.format("makechannel %s", channel));
    }

    public boolean addUser(UserData user){
        return connection.sendCommand(String.format("newaccount %s", user));
    }

    public List<Object> getChannels() {
        return channels;
    }

    public List<UserData> getAllUsersFromServer(){
        if(connection.sendCommand("listaccounts"))
            return users;
        else
            return null;
    }
}
