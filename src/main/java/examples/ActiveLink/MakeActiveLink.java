package examples.ActiveLink;

import TT4J.TeamTalkClient;
import TT4J.TeamTalkConnection;
import TT4J.utils.ConfigurationLoader;
import TT4J.utils.RESTClient;
import TT4J.utils.encryption.RSAHelper;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class MakeActiveLink {

    public static void main(String[] args) throws Exception {

        // Load configuration from file
        ConfigurationLoader cl = new ConfigurationLoader("config.properties");

        // Server connectivity info
        String hostName = cl.getTtHostName();
        int port = cl.getTtPort();

        TeamTalkClient client = new TeamTalkClient(
                new TeamTalkConnection(hostName, port));

        client.registerForAccepted(System.out::println);
        client.registerForAddUserPacket(System.out::println);
        client.registerForAddChannelPacket(System.out::println);

        // Set up link provider;
        LinkProvider linkProvider = new LinkProvider(
                new RSAHelper(cl.getPublicKey(), cl.getPrivateKey()),
                new RESTClient(cl.getRestHostName(), cl.getRestPort()));

        linkProvider.register(client);

        // User info
        String username = "user";
        String password = "password";
        String nick = "Java Admin";

        System.out.println("Connecting: " + client.connect());
        System.out.println("Logging: " + client.login(nick, username, password));

        // Feeding link provider with data
        linkProvider.setChannels(client.getChannels());
        linkProvider.setUsers(client.getAllUsersFromServer());

        while(!linkProvider.isReady()){
           Thread.sleep(100);
        }

        String userName = "a1";
        int channelID = 1;
        int moderatorChannelID = 2;

        System.out.println(linkProvider.getEncodedConnectionString(userName, channelID, moderatorChannelID));
    }
}
