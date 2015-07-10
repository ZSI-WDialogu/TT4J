package examples.ActiveLink;

import TT4J.Event;
import TT4J.TeamTalkClient;
import TT4J.interfaces.Encrypter;
import TT4J.packets.AddChannelPacket;
import TT4J.packets.ServerUpdatePacket;
import TT4J.packets.UserData;
import TT4J.utils.CollectionUtils;
import TT4J.utils.ExceptionUtil;
import TT4J.utils.RESTClient;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class LinkProvider {

    private final static String PREFIX = "wdialogu://%s/%s";

    private Encrypter crypto;
    private RESTClient client;

    protected class State {

        Event<LinkProvider> onReady;

        private LinkProvider data;
        private boolean serverSet = false;
        private boolean userAdded = false;
        private boolean channelAdded = false;

        public State(LinkProvider data) {
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

        private void checkState() {
            if (serverSet && userAdded && channelAdded) {
                onReady.invoke(data);
            }
        }
    }

    private List<UserData> users;
    private List<AddChannelPacket> channels;
    private ServerUpdatePacket server;
    private final ObjectMapper mapper;
    private State state;

    public LinkProvider(Encrypter crypto, RESTClient client) {
        this.client = client;
        this.mapper = new ObjectMapper();
        this.users = new ArrayList<>();
        this.channels = new ArrayList<>();
        this.state = new State(this);
        this.crypto = crypto;
    }

    public void register(TeamTalkClient client) {
        client.registerForServerUpdatePacket(packet -> {
            server = packet;
            state.setServerSet(true);
        });
    }

    public void setUsers(List<UserData> users) {
        this.users = users;
        this.state.setUserAdded(true);
    }

    public void setChannels(List<AddChannelPacket> channels) {
        this.channels = channels;
        this.state.setChannelAdded(true);
    }

    public boolean isReady() {
        return state.onReady.hasBeenInvoked();
    }

    public String getEncodedConnectionString(String userName, int channelId, int modchannelId) throws Exception {
        byte[] rawData = getJSONConnectionSetting(userName, channelId, modchannelId).getBytes();

        String connectionString = Base64.encodeBase64String(rawData);
        String uuid = RESTClient.handleResponse(client.postLink(connectionString)).get(0);
        String resourcePath = client.getResourcePath();

        return String.format(PREFIX, resourcePath, uuid);
    }

    private String getJSONConnectionSetting(String userName, int channelId, int modchannelId) throws Exception {
        ExceptionUtil.require(users, "Users");
        ExceptionUtil.require(channels, "Channels");

        UserData encryptedUser = encryptUser(userName);
        AddChannelPacket encStartUpChannel = encryptChannel(channelId);
        AddChannelPacket encModeratorChannel = encryptChannel(modchannelId);

        return mapper.writeValueAsString(
                new ConnectionSettings(encryptedUser, encStartUpChannel, encModeratorChannel, server));
    }

    private UserData encryptUser(String userName) throws Exception {
        UserData originalUser = CollectionUtils.getFirst(users, u -> u.getUsername().equals(userName));
        return new UserData(originalUser.getUsername(),
                crypto.encryptShort(originalUser.getPassword()) );
    }

    private AddChannelPacket encryptChannel(int channelId) throws Exception {
        AddChannelPacket originalChannel = CollectionUtils.getFirst(channels, c -> c.getChanid() == channelId);
        return new AddChannelPacket(originalChannel.getChanid(),
                crypto.encryptShort(originalChannel.getPassword()));
    }
}


