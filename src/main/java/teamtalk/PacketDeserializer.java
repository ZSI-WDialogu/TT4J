package teamtalk;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.codehaus.jackson.map.ObjectMapper;
import org.reflections.Reflections;
import teamtalk.enums.APINetworkPacketType;
import teamtalk.packets.APINetworkPacket;
import teamtalk.packets.RawPacket;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.reflections.ReflectionUtils.*;

import java.lang.reflect.Modifier;
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
        Reflections reflections = new Reflections("teamtalk.packets");

        Set<Class<? extends APINetworkPacket>> packets =
                reflections.getSubTypesOf(APINetworkPacket.class);

        for(Class<? extends APINetworkPacket> packet: packets){
            try {

                APINetworkPacketType type =  packet.newInstance().getType();
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
            e.printStackTrace();
        }
        return p;
    }
}
