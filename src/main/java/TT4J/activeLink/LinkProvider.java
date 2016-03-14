package TT4J.activeLink;

import TT4J.Event;
import TT4J.TeamTalkClient;
import TT4J.interfaces.Encrypter;
import TT4J.packets.AddChannelPacket;
import TT4J.packets.ServerUpdatePacket;
import TT4J.packets.UserData;
import TT4J.utils.CollectionUtils;
import TT4J.utils.ExceptionUtil;
import TT4J.interfaces.Store;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class LinkProvider {

    private final static String PREFIX = "wdialogu://%s";

    private Store linkStore;
    private Encrypter crypto;
    private final ServerInfo serverInfo;

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

    public LinkProvider(Encrypter crypto, Store linkStore, ServerInfo serverInfo) {
        this.linkStore = linkStore;
        this.serverInfo = serverInfo;
        this.mapper = new ObjectMapper();
        this.users = new ArrayList<>();
        this.channels = new ArrayList<>();
        this.state = new State(this);
        this.crypto = crypto;
    }

    /**
     * Register for server updates
     * @param client TeamTalkClient
     */
    public void register(TeamTalkClient client) {
        client.registerForServerUpdatePacket(packet -> {
            server = packet;
            state.setServerSet(true);
        });
    }

    /**
     * Add List with user data
     * @param users
     */
    public void setUsers(List<UserData> users) {
        this.users = users;
        this.state.setUserAdded(true);
    }

    /**
     * Add list with all channels
     * @param channels
     */
    public void setChannels(List<AddChannelPacket> channels) {
        this.channels = channels;
        this.state.setChannelAdded(true);
    }

    /**
     * Cheks if LinkProvider has established connection with team talk server, and is ready to generate link
     * @return
     */
    public boolean isReady() {
        return state.onReady.hasBeenInvoked();
    }

    /**
     * This method is used to generate encrypted and obfuscated active link for client instance
     * @param userName username for which active link is generated
     * @param channelId id ot the channel on which user will be logged
     * @param expertChannelId  id of the channel with expert
     * @return obfuscated active link
     * @throws Exception
     */
    public String generateActiveLink(String nick, String userName, int channelId, int expertChannelId, String agenda) throws Exception {
        byte[] rawData = getJSONConnectionSetting(nick, userName, channelId, expertChannelId, agenda).getBytes();
        return Base64.encodeBase64String(rawData);
    }


    /**
     * Once active link has been generated, it has to be stored and later queried by client instance
     * @param userName username for which active link is generated
     * @param channelId id ot the channel on which user will be logged
     * @param expertChannelId  id of the channel with expert
     * @return obfuscated active link
     * @return URI used by client instance for REST get queries
     * @throws Exception
     */
    public String getEncodedConnectionString(String nick, String userName, int channelId, int expertChannelId, String agenda) throws Exception {
        String activeLink = generateActiveLink(nick, userName, channelId, expertChannelId, agenda);
        String resourcePath  = linkStore.storeLink(activeLink);

        return String.format(PREFIX, resourcePath);
    }

    private String getJSONConnectionSetting(String nick, String userName, int channelId, int expertChannelId, String agenda) throws Exception {
        ExceptionUtil.require(users, "Users");
        ExceptionUtil.require(channels, "Channels");

        UserData encryptedUser = encryptUser(nick, userName);
        AddChannelPacket encStartUpChannel = encryptChannel(channelId, agenda);
        AddChannelPacket encExpertChannel = encryptChannel(expertChannelId);

        server.setTtEncrypted(serverInfo.isEncrypted());
        server.setTtHostName(serverInfo.getHostName());
        return mapper.writeValueAsString(
                new ConnectionSettings(encryptedUser, encStartUpChannel, encExpertChannel, server));
    }

    private UserData encryptUser(String nick, String userName) throws Exception {
        UserData originalUser = CollectionUtils.getFirst(users, u -> u.getUsername().equals(userName));
        return new UserData(nick, originalUser.getUsername(),
                crypto.encryptShort(originalUser.getPassword()) );
    }

    private AddChannelPacket encryptChannel(int channelId, String agenda) throws Exception {
        AddChannelPacket originalChannel = CollectionUtils.getFirst(channels, c -> c.getChanid() == channelId);
        return new AddChannelPacket(originalChannel.getChanid(),
                crypto.encryptShort(originalChannel.getPassword()),
                agenda);
    }

    private AddChannelPacket encryptChannel(int channelId) throws Exception {
        AddChannelPacket originalChannel = CollectionUtils.getFirst(channels, c -> c.getChanid() == channelId);
        return new AddChannelPacket(originalChannel.getChanid(),
                crypto.encryptShort(originalChannel.getPassword()));
    }
}


