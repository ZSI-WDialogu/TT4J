package TT4J;

import TT4J.enums.APINetworkPacketType;
import TT4J.interfaces.APIConnection;
import TT4J.packets.*;

import java.util.*;
import java.util.function.Consumer;

/**
 * Main TeamTalk server administration client
 */
public class TeamTalkClient {

    private HandshakePacket serverInfo;                                     // object containing basic server information
    private List<UserData> allUsers;                                        // list of all users retrieved from TeamTalk server
    private List<AddUserPacket> loggedUsers;                                // list of all logged users
    private List<AddChannelPacket> channels;                                // list of all channels retrieved from server
    private APIConnection connection;

    private Event<HandshakePacket> onHandShakePacket = new Event<>();
    private Event<ErrorPacket> onErrorPacket = new Event<>();
    private Event<UserData> onUserPacket = new Event<>();
    private Event<AddUserPacket> onAddUserPacket = new Event<>();
    private Event<RemoveUserPacket> onRemoveUserPacket = new Event<>();
    private Event<AddChannelPacket> onAddChannelPacket = new Event<>();
    private Event<AcceptedPacket> onAcceptedPacket = new Event<>();
    private Event<ServerUpdatePacket> onServerUpdatePacket = new Event<>();

    private Map<APINetworkPacketType, Consumer<APINetworkPacket>> packetHandlers;

    /**
     * TeamTalk server administration class
     * @param connection, APIConnection interface used to handle connections with TT server
     */
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
    /**
     * Connect to TT server
     * @return true if client has successfully connected to server, false otherwise
     */
    public boolean connect(){
        return connection.connect();
    }

    /**
     * Close connection with TT server
     */
    public void close() {
        connection.disconnect();
    }

    // REGISTER FOR EVENTS
    /**
     * This event occurs when client has been successfully connected to server
     * and contains basic server information
     * @param consumer an eventhandler for HandShakePacket Event
     */
    public void registerForHandShakePacket(Consumer<HandshakePacket> consumer){
        this.onHandShakePacket.register(consumer);
    }

    /**
     * This event occurs when request - that had been previously send - has not been processed properly
     * and contains error number and message
     * @param consumer an eventhandler for onErrorPacket event
     */
    public void registerForErrorPacket(Consumer<ErrorPacket> consumer){
        this.onErrorPacket.register(consumer);
    }

    /**
     * This event occurs when server is asked for all user accounts and response is received (packet by packet)
     * @param consumer an eventHandler for onUserPacket
     */
    public void registerForUserPacket(Consumer<UserData> consumer){
        this.onUserPacket.register(consumer);
    }

    /**
     * This event occurs when client has successfully logged to server
     * @param consumer an eventhandler for onAcceptedPacket event
     */
    public void registerForAccepted(Consumer<AcceptedPacket> consumer){
        this.onAcceptedPacket.register(consumer);
    }

    /**
     * When user has logged successfully logged in, server sends back packets with basic information
     * For each channel created on server an AddChannelPacket is send to the client
     *
     * @param consumer an eventhandler for onAddChannelPacket event
     */
    public void registerForAddChannelPacket(Consumer<AddChannelPacket> consumer){
        this.onAddChannelPacket.register(consumer);
    }

    /**
     * When user has logged successfully logged in, server sends back packets with basic information
     * For each user logged to the server an AddUserPacket is send to the client
     *
     * @param consumer an eventHandler for AddUserPacket event
     */
    public void registerForAddUserPacket(Consumer<AddUserPacket> consumer){
        this.onAddUserPacket.register(consumer);
    }

    /**
     * When user has logged successfully logged in, server sends back packets with basic information
     * Occurs on login or when server has been updated
     * @param consumer an eventHandler for onServerUpdatePacket
     */
    public void registerForServerUpdatePacket(Consumer<ServerUpdatePacket> consumer){
        this.onServerUpdatePacket.register(consumer);
    }

    /**
     * Occurs when user is removed
     * @param consumer an eventhandler for onRemoveUserPacker event
     */
    public void registerForRemoveUserPacket(Consumer<RemoveUserPacket> consumer){
        this.onRemoveUserPacket.register(consumer);
    }


    // USER COMMAND REGIONS

    /**
     * Log to the server
     * @param nick User nick to be displayed in server
     * @param username User name used in authentication
     * @param password User password used in authentication
     * @return true if login has been successful, false otherwise
     */
    public boolean login(String nick, String username, String password){
         return connection.sendCommand(String.format("login username=\"%s\" password=\"%s\" protocol=\"5.0\" nickname=\"%s\"", username, password, nick));
    }

    /**
     * Create a new channel on server
     * @param channel a channel to bee created on server
     * @return
     */
    public boolean makeChannel(Channel channel){
        return connection.sendCommand(String.format("makechannel %s", channel));
    }

    /**
     * Send text message to given channel
     * @param channelId id of the channel the message is to be send
     * @param message text of the message
     * @return true if message has ben send successfully, false otherwise
     */
    public boolean sendMessage(int channelId, String message){
        return connection.sendCommand( String.format("message type=2 content=\"%s\" chanid=%s", message, channelId));
    }

    /**
     * Create a new user
     * @param user User to be created
     * @return true if user has been created successfully, false otherwise
     */
    public boolean addUser(UserData user){
        return connection.sendCommand(String.format("newaccount %s", user));
    }

    /**
     * Move user to another channel
     * @param userId, id of the to be moved
     * @param channelId id of the channel to which user should be moved
     * @return true if user has been moved successfully, false otherwise
     */
    public boolean moveUser(int userId, int channelId){
        return connection.sendCommand(String.format("moveuser userid=%s chanid=%s", userId, channelId));
    }

    /**
     * Get all channels from server
     * @return List of AddChannelPackets
     */
    public List<AddChannelPacket> getChannels() {
        return channels;
    }

    /**
     * Ask the server for updated list of users
     * @return List of UserDataPacket
     */
    public List<UserData> getAllUsersFromServer(){
        if(connection.sendCommand("listaccounts"))
            return allUsers;
        else
            return null;
    }

    /**
     * Get all logged users
     * @return List of AddUserPackets
     */
    public List<AddUserPacket> getLoggedUsers(){
        return loggedUsers;
    }

    /**
     * Get server info received on connection
     * @return HandshakePacket
     */
    public HandshakePacket getServerInfo() {
        return serverInfo;
    }

    // EVENTS
    private void initialiseEvents() {
         onHandShakePacket = new Event<>();
         onErrorPacket = new Event<>();
         onUserPacket = new Event<>();
         onAddUserPacket = new Event<>();
         onRemoveUserPacket = new Event<>();
         onAddChannelPacket = new Event<>();
         onAcceptedPacket = new Event<>();
         onServerUpdatePacket = new Event<>();
    }

    // PACKET HANDLING
    private void initialiseHandlers() {
        packetHandlers = new HashMap<>();
        packetHandlers.put(APINetworkPacketType.HANDSHAKE, this::handleHandShakePacket);
        packetHandlers.put(APINetworkPacketType.ERROR, this::handleErrorPacket);
        packetHandlers.put(APINetworkPacketType.USER_ACCOUNT, this::handleUserDataPacket);
        packetHandlers.put(APINetworkPacketType.ADD_USER, this::handleAddUserPacket);
        packetHandlers.put(APINetworkPacketType.REMOVE_USER, this::handleRemoveUserPacket);
        packetHandlers.put(APINetworkPacketType.ADD_CHANNEL, this::handleAddChannelPacket);
        packetHandlers.put(APINetworkPacketType.ACCEPTED, this::handleAcceptedPacket);
        packetHandlers.put(APINetworkPacketType.SERVER_UPDATE, this::handleServerUpdatePacket);
    }

    /**
     * Handle events occurring in APIConnection
     * @param packet
     */
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
}
