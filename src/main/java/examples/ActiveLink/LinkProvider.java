package examples.ActiveLink;

import TT4J.Event;
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

    protected class State {

        Event<LinkProvider> onReady;

        private LinkProvider data;
        private boolean serverSet = false;
        private boolean userAdded = false;
        private boolean channelAdded = false;

        public State(LinkProvider data){
            this.data = data;
            this.onReady = new Event<>();
        }

        public void setServerSet(boolean serverSet) {
            this.serverSet = serverSet;
            checkState();
        }

        public void setUserAdded(boolean userAdded) {
            this.userAdded = userAdded;
            checkState();
        }

        public void setChannelAdded(boolean channelAdded) {
            this.channelAdded = channelAdded;
            checkState();
        }

        private void checkState(){
            if(serverSet && userAdded && channelAdded){
                onReady.invoke(data);
            }
        }
    }

    private List<UserData> users;
    private List<AddChannelPacket> channels;
    private ServerUpdatePacket server;
    private final ObjectMapper mapper;
    private State state;

    public LinkProvider(){
        mapper = new ObjectMapper();
        users = new ArrayList<>();
        channels = new ArrayList<>();
        state = new State(this);
    }

    public void register(TeamTalkClient client){
        client.registerForServerUpdatePacket(packet -> {
            server = packet;
            state.setServerSet(true);
        });
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
        this.state.setUserAdded(true);
    }

    public void setChannels(List<AddChannelPacket> channels) {
        this.channels = channels;
        this.state.setChannelAdded(true);
    }

    public boolean isReady(){
        return state.onReady.hasBeenInvoked();
    }
}
