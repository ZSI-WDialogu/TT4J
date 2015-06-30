package examples.ActiveLink;

import TT4J.TeamTalkClient;
import TT4J.TeamTalkConnection;
import utils.ConfigurationLoader;

import java.io.IOException;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class MakeActiveLink {

    public static void main(String[] args) throws IOException {

        // Load configuration from file
        ConfigurationLoader cl = new ConfigurationLoader("config.properties");

        // Server connectivity info
        String hostName = cl.getHostName();
        int port = cl.getPort();

        TeamTalkClient client = new TeamTalkClient(
                new TeamTalkConnection(hostName, port));

        // Set up link provider;
        LinkProvider linkProvider = new LinkProvider();
        linkProvider.register(client);

        // User info
        String username = "user";
        String password = "password";
        String nick = "Java Admin";

        System.out.println("Connecting: " + client.connect());
        System.out.println("Logging: " + client.login(nick, username, password));

        System.out.println(linkProvider.getJSONConnectionSetting("user", 2));

    }
}
