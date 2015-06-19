package teamtalk;

import teamtalk.enums.APINetworkPacketType;
import teamtalk.enums.UserType;
import teamtalk.interfaces.APIConnection;
import teamtalk.packets.APINetworkPacket;
import teamtalk.packets.RawPacket;
import teamtalk.packets.UserData;

import java.util.ArrayList;
import java.util.List;


public class TeamTalkClient {
    // Server info
    private String serverName;
    private int maxUsers;
    private int maxiplogins;
    private int userTimeOut=60;
    private String protocol;

    private List<Object> users;
    private List<Object> channels;
    private APIConnection connection;

    private Event<UserData> userAccountData = new Event<>();

    public TeamTalkClient(APIConnection connection){
        this.connection = connection;
        this.connection.onPacketReceived(this::handlePacket);
        this.userAccountData.register(this::addUser);
    }

    public void close() {
        connection.disconnect();
    }

    private void handlePacket(APINetworkPacket packet){
        if(packet.getType()== APINetworkPacketType.USER_ACCOUNT) {
            userAccountData.invoke((UserData) packet);
        }

        System.out.println(packet);
    }

    private void addUser(UserData packet){
        if(users==null)
            users = new ArrayList<>();

        if(!users.contains(packet)) {
            users.add(packet);
        }
    }

    public boolean connect(){
        return connection.connect();
    }

    public boolean login(String nick, String username, String password){
         return connection.sendCommand(String.format("login username=\"%s\" password=\"%s\" protocol=\"5.0\" nickname=\"%s\"", username, password, nick));
    }

    public boolean makeChannel(Channel channel){
        return connection.sendCommand(String.format("makechannel %s", channel));
    }

    public boolean addUser(String userName, String password, UserType userType, String note, String initialChannel){
        return connection.sendCommand(String.format("newaccount username=\"%s\" password=\"%s\" usertype=%s note=\"%s\" channel=\"%s\"",
                userName, password, userType, note, initialChannel));
    }

    public List<Object> getUsers() {
        return users;
    }

    public void setUsers(List<Object> users) {
        this.users = users;
    }

    public List<Object> getChannels() {
        return channels;
    }

    public void setChannels(List<Object> channels) {
        this.channels = channels;
    }

    public List<Object> getAllUsers(){
        if(connection.sendCommand("listaccounts"))
            return users;
        else
            return null;
    }
}
