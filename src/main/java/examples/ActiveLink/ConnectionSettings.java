package examples.ActiveLink;

import TT4J.JSON.serializers.AddChannelPacketSerializer;
import TT4J.JSON.serializers.ServerUpdatePacketSerializer;
import TT4J.JSON.serializers.UserDataSerializer;
import TT4J.packets.AddChannelPacket;
import TT4J.packets.ServerUpdatePacket;
import TT4J.packets.UserData;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class ConnectionSettings{

    @JsonSerialize(using = UserDataSerializer.class)
    UserData User;

    @JsonSerialize(using = AddChannelPacketSerializer.class)
    AddChannelPacket Channel;

    @JsonSerialize(using = ServerUpdatePacketSerializer.class)
    ServerUpdatePacket Server;

    public ConnectionSettings(UserData user, AddChannelPacket channel, ServerUpdatePacket server){
        this.User = user;
        this.Channel = channel;
        this.Server = server;
    }
}
