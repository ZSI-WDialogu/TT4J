package TT4J.activeLink;

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
    AddChannelPacket StartUpChannel;

    @JsonSerialize(using = AddChannelPacketSerializer.class)
    AddChannelPacket ModeratorChannel;

    @JsonSerialize(using = ServerUpdatePacketSerializer.class)
    ServerUpdatePacket Server;

    public ConnectionSettings(UserData user,
                              AddChannelPacket startUpChannel,
                              AddChannelPacket moderatorChannel,
                              ServerUpdatePacket server){
        this.User = user;
        this.StartUpChannel = startUpChannel;
        this.ModeratorChannel = moderatorChannel;
        this.Server = server;
    }
}
