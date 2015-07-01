import TT4J.utils.ConfigurationLoader;
import TT4J.utils.encryption.PrivateKeyHelper;
import TT4J.utils.encryption.PublicKeyHelper;
import TT4J.utils.encryption.RSAEncryption;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.File;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Extension;
import java.security.interfaces.RSAPrivateCrtKey;

/**
 * Created by Stokowiec on 2015-07-01.
 */
public class RSATests {

    private static final String PUBLIC = "public.key";
    private static final String PRIVATE = "private.key";

    @Test
    public void readWrite() throws Exception{

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair keypair = kpg.generateKeyPair();

        PublicKey publicKey = keypair.getPublic();
        PrivateKey privateKey = keypair.getPrivate();

        PublicKeyHelper.write(publicKey, PUBLIC);
        assert(PublicKeyHelper.read(PUBLIC).equals(publicKey));

        PrivateKeyHelper.write(privateKey, PRIVATE);
        assert(PrivateKeyHelper.read(PRIVATE).equals(privateKey));

        assert (new File(PUBLIC)).delete();
        assert (new File(PRIVATE)).delete();
    }

    @Test
    public void testRSA() throws Exception {

        ConfigurationLoader loader = new ConfigurationLoader("config.properties");
        RSAEncryption rsa = new RSAEncryption(loader);


        String rawString =  "{\"User\":{\"Nick\":\"user\",\"Login\":\"user\",\"Password\":\"password\"},\"Channel\":{\"ID\":2,\"Password\":\"test123\"},\"Server\":{\"IP\":\"153.19.141.166\",\"TCPPort\":7077,\"UDPPort\":7077,\"LocalTcpPort\":7077,\"LocalUdpPort\":7077,\"Encrypted\":false}}";
        String encoded = rsa.encrypt(rawString);
        String decoded = rsa.decrypt(encoded);

        System.out.println(PrivateKeyHelper.toXMLSpec(rsa.getPrivateKey()));
        System.out.println(encoded);
        System.out.println(decoded);


        assert(rawString.equals(decoded));

    }
}
