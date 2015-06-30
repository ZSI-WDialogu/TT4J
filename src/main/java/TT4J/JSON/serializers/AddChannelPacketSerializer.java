package TT4J.JSON.serializers;

import TT4J.packets.AddChannelPacket;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Created by stokowiec on 2015-06-30.
 */
public class AddChannelPacketSerializer  extends JsonSerializer<AddChannelPacket> {

    @Override
    public void serialize(AddChannelPacket value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("ID", value.getChanid());
        jgen.writeStringField("Password", value.getPassword());
        jgen.writeEndObject();
    }
}
