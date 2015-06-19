package teamtalk;

import teamtalk.enums.APINetworkPacketType;
import teamtalk.enums.UserType;
import teamtalk.interfaces.APIConnection;
import teamtalk.packets.*;

import java.util.*;
import java.util.function.Consumer;


public class TeamTalkClient {

    private HandshakePacket serverInfo;
    private List<UserData> users;
    private List<Object> channels;
    private APIConnection connection;

    private Event<UserData> onUserAdded = new Event<>();
    private Event<HandshakePacket> onHandShakePacket = new Event<>();
    private Event<ErrorPacket> onErrorPacket = new Event<>();
    private Event<AcceptedPacket> onAcceptedPacket = new Event<>();
    private Map<APINetworkPacketType, Consumer<APINetworkPacket>> packetHandlers;

    public TeamTalkClient(APIConnection connection){
        this.connection = connection;
        this.connection.onPacketReceived(this::handlePacket);
        this.users = new ArrayList<>();
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
        packetHandlers.put(APINetworkPacketType.ERROR, this::handleErrorPacket);
        packetHandlers.put(APINetworkPacketType.HANDSHAKE, this::handleHandShakePacket);
        packetHandlers.put(APINetworkPacketType.ACCEPTED, this::handleAcceptedPacket);
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

    // EVENTS
    private void initialiseEvents() {
        this.onUserAdded = new Event<>();
        this.onErrorPacket = new Event<>();
        this.onHandShakePacket = new Event<>();
        this.onAcceptedPacket = new Event<>();
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
