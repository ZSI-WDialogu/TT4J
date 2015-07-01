package TT4J.utils.encryption;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class PublicKeyHelper {

    public static PublicKey read(String filename) throws Exception {

        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int)f.length()];
        dis.readFully(keyBytes);
        dis.close();

        return getPublicKey(keyBytes);
    }

    public static PublicKey read(InputStream is) throws Exception {
        byte[] keyBytes = IOUtils.toByteArray(is);
        return getPublicKey(keyBytes);
    }

    public static void write(PublicKey pk, String fileName) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(fileName)) {
            stream.write(pk.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PublicKey getPublicKey(byte[] keyBytes) throws Exception{
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }


}