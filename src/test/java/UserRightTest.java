import TT4J.TeamTalkClient;
import TT4J.TeamTalkConnection;
import TT4J.enums.UserRight;
import TT4J.enums.UserType;
import TT4J.packets.UserData;
import TT4J.utils.ConfigurationLoader;
import TT4J.utils.MapUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stokowiec on 2015-06-26.
 */
public class UserRightTest {

    public static void main(String[] args) throws IOException {

        // Load configuration from file
        ConfigurationLoader conf = new ConfigurationLoader("config.properties");

        // Server info
        String hostName = conf.getTtHostName();
        int portNumber = conf.getTtPort();

        // User info
        String username = "";
        String password = "";
        String nick = "Java Admin";

        TeamTalkClient client = new TeamTalkClient(
                new TeamTalkConnection(hostName, portNumber));

        System.out.println("Connecting: " + client.connect());
        System.out.println("Logging: " + client.login(nick, username, password));

        try {
            // Add
            getRights().forEach( (name, value) -> {
                System.out.println(String.format("Name: %s, value: %s", name, value));
                client.addUser(new UserData(name, "Nick", name, UserType.DEFAULT, "rights test", "1", value));
            });

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static  Map<String, Integer> getRights() throws IllegalAccessException {
        Field[] declaredFields = UserRight.class.getDeclaredFields();
        Map<String, Integer> rights = new HashMap<>();
        for (Field field : declaredFields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                String name = field.getName();
                int value = field.getInt(null);
                rights.put(name, value);
            }
        }
        return MapUtil.sortByValue(rights);
    }
}
