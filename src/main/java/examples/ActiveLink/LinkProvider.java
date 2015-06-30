package examples.ActiveLink;

import TT4J.TeamTalkClient;
import TT4J.packets.AddChannelPacket;
import TT4J.packets.ServerUpdatePacket;
import TT4J.packets.UserData;
import TT4J.utils.CollectionUtils;
import TT4J.utils.ExceptionUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class LinkProvider {

    private List<UserData> users;
    private List<AddChannelPacket> channels;
    private ServerUpdatePacket server;
    private final ObjectMapper mapper;

    public LinkProvider(){
        mapper = new ObjectMapper();
        users = new ArrayList<>();
        channels = new ArrayList<>();
    }

    public void register(TeamTalkClient client){
        client.registerForServerUpdatePacket(packet -> this.server = packet);
        client.registerForAddChannel(channels::add);
        client.registerForUserData(users::add);
    }

    public String getJSONConnectionSetting(String userName, int channelId) throws IOException {
        ExceptionUtil.require(users, "Users");
        ExceptionUtil.require(channels, "Channels");

        UserData user = CollectionUtils.getFirst(users, u -> u.getUsername().equals(userName));
        AddChannelPacket channel = CollectionUtils.getFirst(channels, c -> c.getChanid() == channelId);

        String result = mapper.writeValueAsString(new ConnectionSettings(user, channel, server));
        return result;
    }

    public void setUsers(List<UserData> users) {
        this.users = users;
    }

    public void setChannels(List<AddChannelPacket> channels) {
        this.channels = channels;
    }
}
