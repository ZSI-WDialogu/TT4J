package TT4J.JSON.serializers;


import TT4J.packets.UserData;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Created by stokowiec on 2015-06-30.
 */
public class UserDataSerializer extends JsonSerializer<UserData> {

    @Override
    public void serialize(UserData value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("Nick", value.getUsername());
        jgen.writeStringField("Login", value.getUsername());
        jgen.writeStringField("Password", value.getPassword());
        jgen.writeEndObject();
    }
}
