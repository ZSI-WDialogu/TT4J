import teamtalk.TeamTalkClient;
import teamtalk.TeamTalkConnection;
import teamtalk.enums.UserRight;
import teamtalk.enums.UserType;
import teamtalk.packets.UserData;
import teamtalk.utils.MapUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stokowiec on 2015-06-26.
 */
public class UserRightTest {

    public static void main(String[] args) throws IOException {

        // Server info
        String hostName = "";
        int portNumber = 7077;

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
                client.addUser(new UserData(name, name, UserType.DEFAULT, "rights test", "1", value));
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
