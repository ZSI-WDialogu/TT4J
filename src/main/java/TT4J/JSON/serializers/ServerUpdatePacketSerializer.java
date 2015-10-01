package TT4J.JSON.serializers;

import TT4J.packets.ServerUpdatePacket;
import TT4J.utils.ConfigurationLoader;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Used in ActiveLink generation
 */
public class ServerUpdatePacketSerializer extends JsonSerializer<ServerUpdatePacket> {

    @Override
    public void serialize(ServerUpdatePacket value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {


        jgen.writeStartObject();
        jgen.writeStringField("IP",value.getTtHostName());
        jgen.writeNumberField("TCPPort", value.getTcpport());
        jgen.writeNumberField("UDPPort", value.getUdpport());
        jgen.writeNumberField("LocalTcpPort", value.getTcpport());
        jgen.writeNumberField("LocalUdpPort", value.getUdpport());
        jgen.writeBooleanField("Encrypted", value.isTtEncrypted());

        jgen.writeEndObject();

    }
}
