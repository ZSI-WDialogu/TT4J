package TT4J.utils.encryption;


import org.apache.commons.io.IOUtils;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateKeyHelper {

    public static PrivateKey read(String filename) throws Exception {

        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int)f.length()];
        dis.readFully(keyBytes);
        dis.close();

        return getPrivateKey(keyBytes);
    }

    public static PrivateKey read(InputStream is) throws Exception {
        byte[] keyBytes = IOUtils.toByteArray(is);
        return getPrivateKey(keyBytes);
    }

    public static void write(PrivateKey pk, String fileName) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(fileName)) {
            stream.write(pk.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PrivateKey getPrivateKey(byte[] keyBytes) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}