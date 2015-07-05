import examples.AgendaExample.StringCompression;
import org.junit.Test;

/**
 * Created by Stokowiec on 2015-07-01.
 */
public class StringCompressionTests {
    @Test
    public void testCompression() throws Exception {

        String rawString =  "{\"User\":{\"Nick\":\"user\",\"Login\":\"user\",\"Password\":\"password\"},\"Channel\":{\"ID\":2,\"Password\":\"test123\"},\"Server\":{\"IP\":\"153.19.141.166\",\"TCPPort\":7077,\"UDPPort\":7077,\"LocalTcpPort\":7077,\"LocalUdpPort\":7077,\"Encrypted\":false}}";

        byte[] compressed = StringCompression.compress(rawString);
        byte[] raw = rawString.getBytes();
        String decompressed = StringCompression.decompress(compressed);

        assert(rawString.equals(decompressed));
        assert(compressed.length < raw.length);
    }

    @Test
    public void testStringCompression() throws Exception {

        String rawString =  "{\"User\":{\"Nick\":\"user\",\"Login\":\"user\",\"Password\":\"password\"},\"Channel\":{\"ID\":2,\"Password\":\"test123\"},\"Server\":{\"IP\":\"153.19.141.166\",\"TCPPort\":7077,\"UDPPort\":7077,\"LocalTcpPort\":7077,\"LocalUdpPort\":7077,\"Encrypted\":false}}";

        String compressed = StringCompression.compressToString(rawString);
        String decompressed = StringCompression.decompressFromString(compressed);

        assert(rawString.equals(decompressed));
    }
}
