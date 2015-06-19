package teamtalk;

import teamtalk.enums.AudioCodec;
import teamtalk.enums.UserType;
import teamtalk.packets.UserData;

public class Program {

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

            System.out.println("Connecting: " + client.connect());
            System.out.println("Logging: " + client.login(nick, username, password));

            // How to create new user
            client.addUser(
                    new UserData("New java user 2", "123", UserType.DEFAULT, "new user", "1"));

            // How to display all accounts
            System.out.println("List accounts: ");
            client.getAllUsersFromServer().forEach(System.out::println);


            // Hot to make new channel

            client.makeChannel(new Channel(1, true, "api channel 3", "123", "Java docs", AudioCodec.SpeexVBR));
            client.close();
	}
}
