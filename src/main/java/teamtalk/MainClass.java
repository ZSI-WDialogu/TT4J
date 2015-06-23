package teamtalk;

import teamtalk.enums.AudioCodec;
import teamtalk.enums.UserType;
import teamtalk.packets.AddUserPacket;
import teamtalk.packets.UserData;

import java.util.ArrayList;
import java.util.List;

public class MainClass {

        public static void main(String[] args) {

            // Server info
            String hostName = "153.19.141.166";
            int portNumber = 7077;

            // User info
            String username = "100KO";
            String password = "100KO";
            String nick = "Java Admin";

            TeamTalkClient client = new TeamTalkClient(
                    new TeamTalkConnection(hostName, portNumber));

            // Register for channel updates
            client.registerForAddChannel( packet ->  System.out.println("Event: " + packet));

            // Register for errors
            client.registerForErrorPacket(error -> System.out.println("Error: " + error));

            // Register for logged user
            client.registerForAddUserPacker( packet -> System.out.println("User: " + packet));

            System.out.println("Connecting: " + client.connect());
            System.out.println("Logging: " + client.login(nick, username, password));

            // How to create new user
            client.addUser(
                new UserData("New java user 3", "123", UserType.DEFAULT, "new user", "1"));

            // How to send message: it can be used to update meeting agenda
            client.sendMessage(1, "It's a Me: a message!");

            // How to display all accounts
            System.out.println("List accounts: ");
            client.getAllUsersFromServer().forEach(System.out::println);

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
            client.makeChannel(new Channel(1, true, "api channel 5", "123", "Java docs", AudioCodec.SpeexVBR));
            client.close();

	}
}
