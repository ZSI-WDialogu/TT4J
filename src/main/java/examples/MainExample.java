package examples;

import TT4J.Channel;
import TT4J.TeamTalkClient;
import TT4J.TeamTalkConnection;
import TT4J.enums.AudioCodec;
import TT4J.enums.UserRight;
import TT4J.enums.UserType;
import TT4J.packets.AddUserPacket;
import TT4J.packets.UserData;
import TT4J.utils.ConfigurationLoader;

import java.util.ArrayList;
import java.util.List;

public class MainExample {

        public static void main(String[] args) {

            // Load configuration from file
            ConfigurationLoader cl = new ConfigurationLoader("config.properties");

            // Server connectivity info
            String hostName = cl.getTtHostName();
            int port = cl.getTtPort();

            // User info
            String username = "user";
            String password = "password";
            String nick = "Java Admin";

            TeamTalkClient client = new TeamTalkClient(
                    new TeamTalkConnection(hostName, port));

            // Register for channel updates
            client.registerForAddChannelPacket(packet -> System.out.println("Event: " + packet));

            // Register for errors
            client.registerForErrorPacket(error -> System.out.println("Error: " + error));

            // Register for logged user
            client.registerForAddUserPacket(packet -> System.out.println("User: " + packet));

            System.out.println("Connecting: " + client.connect());
            System.out.println("Logging: " + client.login(nick, username, password));

            // How to create new user
            client.addUser(
                new UserData("New java user 4", "Nick", "123", UserType.DEFAULT, "new user", "1", UserRight.getDefaultRights()));

            // How to send message: it can be used to update meeting agenda
            client.sendMessage(1, "It's a Me: a message!");

            // How to display all accounts
            System.out.println("List accounts: ");
            List<UserData> users = client.getAllUsersFromServer();
            users.forEach(System.out::println);

            // How to display all channels
            System.out.println("List channels: ");
            client.getChannels().forEach(System.out::println);

            // How to display logged account
            System.out.println("List logged users: ");
            client.getLoggedUsers().forEach(System.out::println);

            // Move all users to one channel
            System.out.println("Move users...");
            List<AddUserPacket> list = new ArrayList(client.getLoggedUsers());
            list.forEach(user -> System.out.println(client.moveUser(user.getUserid(), 2)));

            // Check if users have been moved
            System.out.println("List logged users: ");
            client.getLoggedUsers().forEach(System.out::println);

            // Hot to make new channel
            client.makeChannel(new Channel(1, true, true, "api channel 5", "123", "Java docs", AudioCodec.SpeexVBR));
            client.close();

	}
}
