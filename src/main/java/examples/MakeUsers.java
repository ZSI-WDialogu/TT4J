package examples;

import TT4J.TeamTalkClient;
import TT4J.TeamTalkConnection;
import TT4J.enums.UserRight;
import TT4J.enums.UserType;
import TT4J.packets.UserData;
import utils.ConfigurationLoader;

public class MakeUsers {

    public static void main(String[] args) {

        // Load configuration from file
        ConfigurationLoader cl = new ConfigurationLoader("config.properties");

        // Server connectivity info
        String hostName = cl.getHostName();
        int port = cl.getPort();

        // User info
        String username = "user";
        String password = "password";
        String nick = "Java Admin";

        TeamTalkClient client = new TeamTalkClient(
                new TeamTalkConnection(hostName, port));

        client.registerForServerUpdatePacket( packet ->
            System.out.println("Server: " + packet.toString())
        );

        System.out.println("Connecting: " + client.connect());
        System.out.println("Logging: " + client.login(nick, username, password));

        // How to create new user
        for(int i =0; i < 20; i++) {
            client.addUser(
                    new UserData("New java user " + i, "123", UserType.DEFAULT, "new user", "1", UserRight.getDefaultRights()));
        }
    }
}
