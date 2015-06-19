import org.codehaus.jackson.map.ObjectMapper;
import teamtalk.packets.AddChannelPacket;

import java.io.IOException;

public class PacketDeserializerTests {
    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String json  = "{ \"audiocfg\" : [0,8000],\"channel\" : \"/test_02/\",\"maxusers\" : 1000,\"type\" : 1,\"parentid\" : 1,\"oppassword\" : \"test123o\",\"audiocodec\" : \"[3,48000,1,2048,10,1,0,32000,1,0,1920]\",\"userdata\" : 0,\"password\" : \"test123\",\"diskquota\" : 0,\"protected\" : 1,\"operators\" : [],\"topic\" : \"We are going to test channel 2\",\"chanid\" : 3 }";
        AddChannelPacket packet =  mapper.readValue(json, AddChannelPacket.class);
    }
}
