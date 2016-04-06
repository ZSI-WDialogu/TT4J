package TT4J.JSON.serializers;

import TT4J.packets.AddChannelPacket;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Used in ActiveLink generation
 */
public class AddChannelPacketSerializer  extends JsonSerializer<AddChannelPacket> {

    @Override
    public void serialize(AddChannelPacket value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("ID", value.getChanid());
        jgen.writeStringField("Password", value.getPassword());
        jgen.writeStringField("Agenda", value.getAgenda());
        jgen.writeStringField("StartDate", value.getStartDate());
        jgen.writeStringField("EndDate", value.getEndDate());
        jgen.writeStringField("ExpertsPanelModeratorLogin", value.getExpertsPanelModerator());
        jgen.writeArrayFieldStart("ExpertLogins");
        if(value.getExpertLogins() != null){
            for (String expertLogin : value.getExpertLogins()){
                jgen.writeString(expertLogin);
            }
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}
