package teamtalk;

import teamtalk.enums.UserType;

public class Program {

        public static void main(String[] args) {

            // Server info
            String hostName = "153.19.141.166";
            int portNumber = 7077;

            // User info
            String username = "100KO";
            String password = "100KO";
            String nick = "Java Admin";

            TeamTalkClient client = new TeamTalkClient(new TeamTalkConnection(hostName, portNumber));

            System.out.println("Connecting: " + client.connect());
            System.out.println("Logging: " + client.login(nick, username, password));

            client.addUser("New java user 2", "test123", UserType.DEFAULT, "Nothing to add", "1");


            System.out.println("List accounts: " + client.getAllUsers());
            //client.makeChannel(new Channel(1, true, "api channel 2", "123", "Java docs", AudioCodec.SpeexVBR));
            //client.close();
	}
}
