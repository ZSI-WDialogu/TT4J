package TT4J.JSON.deserializers;

import TT4J.enums.AudioCodec;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class AudioCodecDeserializer extends JsonDeserializer<AudioCodec> {

    @Override
    public AudioCodec deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String text = jp.getText();
        return AudioCodec.fromString(text);
    }
}
