package examples.AgendaExample;

import TT4J.TeamTalkClient;
import TT4J.TeamTalkConnection;
import TT4J.utils.ConfigurationLoader;
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
            String agenda = StringCompression.compressToStringCSharp(text);
            return String.format("AGENDA:%s", agenda);
        }
    }

    public static void main(String[] args) throws Exception {

        // Load configuration from file
        ConfigurationLoader cl = new ConfigurationLoader("config.properties");

        // Server connectivity info
        String hostName = cl.getHostName();
        int port = cl.getPort();

        TeamTalkClient client = new TeamTalkClient(
                new TeamTalkConnection(hostName, port));

        client.registerForAccepted(System.out::println);
        client.registerForAddUserPacket(System.out::println);
        client.registerForAddChannelPacket(System.out::println);

        // Set up link provider;
        LinkProvider linkProvider = new LinkProvider(
                new RSAHelper(cl));

        linkProvider.register(client);

        // User info
        String username = "user";
        String password = "password";
        String nick = "Java Admin";

        System.out.println("Connecting: " + client.connect());
        System.out.println("Logging: " + client.login(nick, username, password));

        client.sendMessage(1, Agenda.fromString(RTF.example()));

    }

}
