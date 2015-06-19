import com.sun.javaws.exceptions.InvalidArgumentException;
import teamtalk.PacketDeserializer;
import teamtalk.packets.APINetworkPacket;
import teamtalk.packets.RawPacket;

/**
 * Created by stokowiec on 2015-06-19.
 */

public class PacketDeserializerTests {
    public static void main(String[] args) {
        PacketDeserializer pd = new PacketDeserializer();

        RawPacket rp = new RawPacket("addchannel channel=\"/\" chanid=1 parentid=0 password=\"\" oppassword=\"\" protected=0 topic=\"\" operators=[] diskquota=0 maxusers=1000 type=1 userdata=0 audiocodec=[3,48000,1,2048,10,1,0,32000,1,0,1920] audiocfg=[0,0]");
        try {
            APINetworkPacket p=  pd.deserialize(rp);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

    }
}
