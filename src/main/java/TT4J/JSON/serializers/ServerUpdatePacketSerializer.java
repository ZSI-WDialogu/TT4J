package TT4J.JSON.serializers;

import TT4J.packets.ServerUpdatePacket;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import utils.ConfigurationLoader;

import java.io.IOException;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class ServerUpdatePacketSerializer extends JsonSerializer<ServerUpdatePacket> {

    @Override
    public void serialize(ServerUpdatePacket value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        // Load configuration from file
        ConfigurationLoader cl = new ConfigurationLoader("config.properties");

        jgen.writeStartObject();
        jgen.writeStringField("IP",cl.getHostName());
        jgen.writeNumberField("TCPPort", value.getTcpport());
        jgen.writeNumberField("UDPPort", value.getUdpport());
        jgen.writeNumberField("LocalTcpPort", value.getTcpport());
        jgen.writeNumberField("LocalUdpPort", value.getUdpport());
        jgen.writeBooleanField("Encrypted", cl.isEncrypted());

        jgen.writeEndObject();

    }
}
