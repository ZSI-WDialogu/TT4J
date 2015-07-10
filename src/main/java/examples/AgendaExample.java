package examples;

import TT4J.TeamTalkClient;
import TT4J.TeamTalkConnection;
import TT4J.utils.ConfigurationLoader;
import TT4J.utils.RESTClient;
import TT4J.utils.StringCompression;
import TT4J.utils.encryption.RSAHelper;
import examples.ActiveLink.LinkProvider;

import static com.tutego.jrtf.Rtf.rtf;
import static com.tutego.jrtf.RtfPara.p;
import static com.tutego.jrtf.RtfText.*;

/**
 * Created by Stokowiec on 2015-07-01.
 */
public class AgendaExample {

    public static class RTF {
        //https://github.com/ullenboom/jrtf
        public static String example() {
            return rtf().section(
                    p( "First paragraph" ),
                    p( tab(),
                            "Second par ",
                            bold( "with something in bold" ),
                            text( " and " ),
                            italic( underline( "italic underline" ) )
                    )
            ).toString();
        }
    }

    public static class Agenda {
        public static String fromString(String text) throws Exception {
            return String.format("AGENDA:%s", StringCompression.compressCsCompatible(text));
        }
    }

    public static void main(String[] args) throws Exception {

        // Load configuration from file
        ConfigurationLoader cl = new ConfigurationLoader("config.properties");

        // Server connectivity info
        String hostName = cl.getTtHostName();
        int port = cl.getTtPort();

        // Create client
        TeamTalkClient client = new TeamTalkClient(new TeamTalkConnection(hostName, port));

        // Set up link provider;
        LinkProvider linkProvider = new LinkProvider(
                new RSAHelper(cl),
                new RESTClient(cl.getRestHostName(), cl.getRestPort()));

        linkProvider.register(client);

        // User info
        String username = "user";
        String password = "password";
        String nick = "Java Admin";

        System.out.println("Connecting: " + client.connect());
        System.out.println("Logging: " + client.login(nick, username, password));

        // First we have to create agenda in rtf format
        String rtf = RTF.example();

        // Secondly we have to sent appropriate message
        // To this end: compress string, and prepend it with "AGENDA:"
        client.sendMessage(1, Agenda.fromString(rtf));

    }

}
