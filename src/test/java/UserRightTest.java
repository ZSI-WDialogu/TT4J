import teamtalk.TeamTalkClient;
import teamtalk.TeamTalkConnection;
import teamtalk.enums.UserRight;
import teamtalk.utils.MapUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stokowiec on 2015-06-26.
 */
public class UserRightTest {

    public static void main(String[] args) throws IOException {

//        // Server info
//        String hostName = "153.19.141.166";
//        int portNumber = 7077;
//
//        // User info
//        String username = "stoko";
//        String password = "stoko";
//        String nick = "Java Admin";
//
//        TeamTalkClient client = new TeamTalkClient(
//                new TeamTalkConnection(hostName, portNumber));

        try {

            getRights().forEach( (name, value) -> {
                System.out.println(String.format("Name: %s, value: %s", name, value));
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
