package TT4J;

import TT4J.enums.APINetworkPacketType;
import TT4J.interfaces.APIConnection;
import TT4J.packets.*;

import java.util.*;
import java.util.function.Consumer;


public class TeamTalkClient {

    private HandshakePacket serverInfo;
    private List<UserData> allUsers;
    private List<AddUserPacket> loggedUsers;
    private List<AddChannelPacket> channels;
    private APIConnection connection;

    private Event<UserData> onUserPacket = new Event<>();
    private Event<AddUserPacket> onAddUserPacket = new Event<>();
    private Event<RemoveUserPacket> onRemoveUserPacket = new Event<>();
    private Event<AddChannelPacket> onAddChannelPacket = new Event<>();
    private Event<HandshakePacket> onHandShakePacket = new Event<>();
    private Event<ErrorPacket> onErrorPacket = new Event<>();
    private Event<AcceptedPacket> onAcceptedPacket = new Event<>();
    private Event<ServerUpdatePacket> onServerUpdatePacket = new Event<>();

    private Map<APINetworkPacketType, Consumer<APINetworkPacket>> packetHandlers;

    public TeamTalkClient(APIConnection connection){
        this.connection = connection;
        this.connection.onPacketReceived(this::handlePacket);
        this.allUsers = new ArrayList<>();
        this.channels = new ArrayList<>();
        this.loggedUsers = new ArrayList<>();
        initialiseEvents();
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
        packetHandlers.put(APINetworkPacketType.ADD_USER, this::handleAddUserPacket);
        packetHandlers.put(APINetworkPacketType.ERROR, this::handleErrorPacket);
        packetHandlers.put(APINetworkPacketType.HANDSHAKE, this::handleHandShakePacket);
        packetHandlers.put(APINetworkPacketType.ACCEPTED, this::handleAcceptedPacket);
        packetHandlers.put(APINetworkPacketType.SERVER_UPDATE, this::handleServerUpdatePacket);
        packetHandlers.put(APINetworkPacketType.ADD_CHANNEL, this::handleAddChannelPacket);
        packetHandlers.put(APINetworkPacketType.REMOVE_USER, this::handleRemoveUserPacket);
    }

    private void handlePacket(APINetworkPacket packet){
        Consumer<APINetworkPacket> handler = packetHandlers.getOrDefault(packet.getPacketType(), null);
        if(handler!=null){
            handler.accept(packet);
        }
    }
    private void handleUserDataPacket(APINetworkPacket packet){
        UserData data = (UserData) packet;
        if(data != null && !allUsers.contains(packet)) {
            allUsers.add(data);
            onUserPacket.invoke(data);
        }
    }
    private void handleErrorPacket(APINetworkPacket packet){
        ErrorPacket error = (ErrorPacket) packet;
        if(error!=null){
            onErrorPacket.invoke(error);
        }
    }
    private void handleHandShakePacket(APINetworkPacket packet){
        HandshakePacket handShake = (HandshakePacket) packet;
        if(handShake!=null){
            serverInfo = handShake;
            onHandShakePacket.invoke(handShake);
        }
    }
    private void handleAcceptedPacket(APINetworkPacket packet){
        AcceptedPacket p = (AcceptedPacket) packet;
        if(p!=null){
            onAcceptedPacket.invoke(p);
        }
    }
    private void handleServerUpdatePacket(APINetworkPacket packet){
        ServerUpdatePacket p = (ServerUpdatePacket) packet;
        if(p!=null){
            onServerUpdatePacket.invoke(p);
        }
    }
    private void handleAddChannelPacket(APINetworkPacket packet){
        AddChannelPacket p = (AddChannelPacket) packet;
        if(p!=null && !channels.contains(p)){
            onAddChannelPacket.invoke(p);
            channels.add(p);
        }

    }
    private synchronized void handleAddUserPacket(APINetworkPacket packet) {
        AddUserPacket p = (AddUserPacket) packet;
        if(p!=null && !loggedUsers.contains(packet)){
            loggedUsers.add(p);
            onAddUserPacket.invoke(p);
        }
    }
    private synchronized void handleRemoveUserPacket(APINetworkPacket packet) {
        RemoveUserPacket p = (RemoveUserPacket) packet;
        if(p!=null){
            loggedUsers.removeIf(user -> user.getUserid() == p.getUserid());
            onRemoveUserPacket.invoke(p);
        }
    }

    // EVENTS
    private void initialiseEvents() {
        this.onUserPacket = new Event<>();
        this.onErrorPacket = new Event<>();
        this.onHandShakePacket = new Event<>();
        this.onAcceptedPacket = new Event<>();
        this.onServerUpdatePacket = new Event<>();
        this.onAddUserPacket = new Event<>();
        this.onRemoveUserPacket = new Event<>();
    }

    // REGISTER FOR EVENTS
    public void registerForHandShakePacket(Consumer<HandshakePacket> consumer){
        this.onHandShakePacket.register(consumer);
    }

    public void registerForErrorPacket(Consumer<ErrorPacket> consumer){
        this.onErrorPacket.register(consumer);
    }

    public void registerForHandShake(Consumer<HandshakePacket> consumer){
        this.onHandShakePacket.register(consumer);
    }

    public void registerForAccepted(Consumer<AcceptedPacket> consumer){
        this.onAcceptedPacket.register(consumer);
    }

    public void registerForAddChannel(Consumer<AddChannelPacket> consumer){
        this.onAddChannelPacket.register(consumer);
    }

    public void registerForAddUserPacker(Consumer<AddUserPacket> consumer){
        this.onAddUserPacket.register(consumer);
    }

    public void registerForRemoveUserPacket(Consumer<RemoveUserPacket> consumer){
        this.onRemoveUserPacket.register(consumer);
    }

    public void registerForServerUpdatePacket(Consumer<ServerUpdatePacket> consumer){
        this.onServerUpdatePacket.register(consumer);
    }

    // USER COMMAND REGIONS
    public boolean login(String nick, String username, String password){
         return connection.sendCommand(String.format("login username=\"%s\" password=\"%s\" protocol=\"5.0\" nickname=\"%s\"", username, password, nick));
    }

    public boolean makeChannel(Channel channel){
        return connection.sendCommand(String.format("makechannel %s", channel));
    }

    public boolean sendMessage(int channelId, String message){
        return connection.sendCommand( String.format("message type=2 content=\"%s\" chanid=%s", message, channelId));
    }

    public boolean addUser(UserData user){
        return connection.sendCommand(String.format("newaccount %s", user));
    }

    public boolean moveUser(int userId, int channelId){
        return connection.sendCommand(String.format("moveuser userid=%s chanid=%s", userId, channelId));
    }

    public List<AddChannelPacket> getChannels() {
        return channels;
    }

    public List<UserData> getAllUsersFromServer(){
        if(connection.sendCommand("listaccounts"))
            return allUsers;
        else
            return null;
    }

    public List<AddUserPacket> getLoggedUsers(){
        return loggedUsers;
    }

    public HandshakePacket getServerInfo() {
        return serverInfo;
    }
}
