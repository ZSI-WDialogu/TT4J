package TT4J;

import TT4J.enums.APINetworkPacketType;
import TT4J.packets.APINetworkPacket;
import TT4J.packets.RawPacket;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.codehaus.jackson.map.ObjectMapper;
import org.reflections.Reflections;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class PacketDeserializer {

    private ObjectMapper mapper;
    private Map<APINetworkPacketType, Class<? extends APINetworkPacket>> classes;

    public PacketDeserializer(){
        mapper= new ObjectMapper();
        classes = initializeMap();
    }

    private Map<APINetworkPacketType, Class<? extends APINetworkPacket>> initializeMap() {

        Map<APINetworkPacketType, Class<? extends APINetworkPacket>> classes = new HashMap<>();
        Reflections reflections = new Reflections("TT4J.packets");

        Set<Class<? extends APINetworkPacket>> packets =
                reflections.getSubTypesOf(APINetworkPacket.class);

        for(Class<? extends APINetworkPacket> packet: packets){
            try {

                APINetworkPacketType type =  packet.newInstance().getPacketType();
                classes.put(type, packet);

            } catch (InstantiationException | IllegalAccessException  e) {
                e.printStackTrace();
            }
        }

        return classes;
    }

    public APINetworkPacket deserialize(RawPacket packet) throws InvalidArgumentException {

        APINetworkPacketType type = packet.type();
        if(!classes.containsKey(type))
            throw new InvalidArgumentException(new String[]{"Packet not registered"});

        String serializedPacket = packet.toString();

        APINetworkPacket p = null;
        try {
            p = mapper.readValue(serializedPacket, classes.get(type));
        } catch (IOException e) {
            throw new InvalidArgumentException(new String[]{"Cannot deserialize"});
        }
        return p;
    }
}
