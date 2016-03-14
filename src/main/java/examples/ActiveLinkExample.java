package examples;

import TT4J.TeamTalkClient;
import TT4J.TeamTalkConnection;
import TT4J.activeLink.LinkProvider;
import TT4J.activeLink.RESTStore;
import TT4J.activeLink.ServerInfo;
import TT4J.utils.ConfigurationLoader;
import TT4J.utils.encryption.RSAHelper;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class ActiveLinkExample {

    public static void main(String[] args) throws Exception {

        // Load configuration from file
        ConfigurationLoader cl = new ConfigurationLoader("example.properties");

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
                new RESTStore(cl.getRestHostName(), cl.getRestPort()),
                new ServerInfo(hostName, port, cl.isTtEncrypted()));

        linkProvider.register(client);

        String username = "tt";
        String password = "tt123";
        String nick = "Admin";

        System.out.println("Connecting: " + client.connect());
        System.out.println("Logging: " + client.login(nick, username, password));

        // Feeding link provider with data
        linkProvider.setChannels(client.getChannels());
        linkProvider.setUsers(client.getAllUsersFromServer());

        while(!linkProvider.isReady()){
            Thread.sleep(100);
        }

        String userNickName = "test_user_nick";
        String userName = "test_user_1";
        int channelID = 2;
        int expertChannelID = 3;
        String agenda = "agenda";

        System.out.println(linkProvider.getEncodedConnectionString(userNickName, userName, channelID, expertChannelID, agenda));
    }
}
