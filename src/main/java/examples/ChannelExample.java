package examples;

import TT4J.Channel;
import TT4J.TeamTalkClient;
import TT4J.TeamTalkConnection;
import TT4J.enums.AudioCodec;
import TT4J.utils.ConfigurationLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stokowiec on 2015-07-16.
 */
public class ChannelExample {

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

        System.out.println("Connecting: " + client.connect());
        System.out.println("Logging: " + client.login(nick, username, password));

        // Register for channel updates
        List<Integer> channels = new ArrayList<>();

        client.registerForAddChannelPacket(packet -> {
            System.out.println("Event: " + packet);
            channels.add(packet.getChanid());
        });

        client.registerForRemoveChannelPacket(packet -> System.out.println("Event: " + packet));

        // Display all channels before adding channel
        System.out.println("Before adding channel: ");
        client.getChannels().forEach(System.out::println);

        // Add new channel
        client.makeChannel(new Channel(1, true, "Channel to be deleted", "123", "Java docs", AudioCodec.SpeexVBR));

        // Display all channels before adding channel
        System.out.println("After adding channel: ");
        client.getChannels().forEach(System.out::println);

        channels.forEach(client::removeChannel);

        client.removeChannel(14);

        // Display all channels before adding channel
        System.out.println("After deleting channel: ");
        client.getChannels().forEach(System.out::println);

        client.close();
    }
}
