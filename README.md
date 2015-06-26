# TT4J

## Team Talk For Java.

### Creating a client instance
``` java
// Server info
String hostName = "xxx.xxx.xxx.xxx";
int portNumber = 7077;

// User info
String username = "user";
String password = "passwd";
String nick = "Java Admin";

TeamTalkClient client = new TeamTalkClient(
        new TeamTalkConnection(hostName, portNumber));
```

### Register for sample events
``` java
// Register for channel updates
client.registerForAddChannel( packet ->  System.out.println("Event: " + packet));

// Register for errors
client.registerForErrorPacket(error -> System.out.println("Error: " + error));

// Register for logged user
client.registerForAddUserPacker( packet -> System.out.println("User: " + packet));
```

### Connecting and logging
``` java

            System.out.println("Connecting: " + client.connect());
            System.out.println("Logging: " + client.login(nick, username, password));

```
### Creating a new user
``` java
// How to create new user
client.addUser(
    new UserData("New java user 3", "123", UserType.DEFAULT, 
                 "new user", "1",  UserRight.getDefaultRights()));
```
### Sending message to a channel
This can be used to send agenda updates from channel operator
``` java
            // How to send message: it can be used to update meeting agenda
            client.sendMessage(1, "It's a Me: a message!");
```

### Displaying all accounts
``` java
            // How to display all accounts
            System.out.println("List accounts: ");
            client.getAllUsersFromServer().forEach(System.out::println);
```

### Displaying all channels
``` java
            // How to display all channels
            System.out.println("List channels: ");
            client.getChannels().forEach(System.out::println);
```

### Displaying all accounts
``` java
            // How to display logged account
            System.out.println("List logged users: ");
            client.getLoggedUsers().forEach(System.out::println);
```

### Moving all users to another channel
``` java
            // Move all users to one channel
            System.out.println("Move users...");
            List<AddUserPacket> list = new ArrayList(client.getLoggedUsers());
            list.forEach(user -> System.out.println(client.moveUser(user.getUserid(), 2)));
            // Check if users have been moved
            System.out.println("List logged users: ");
            client.getLoggedUsers().forEach(System.out::println);
```

### Creating a new channel
``` java
            // Hot to make new channel
            client.makeChannel(
            	new Channel(1, true, "api channel 5", "123", "Java docs",
                	AudioCodec.SpeexVBR));
           
           client.close();
```
