package TT4J.utils.encryption;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
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

    public static String toXMLSpec(PrivateKey pk) throws Exception {
        RSAPrivateCrtKey privKey = (RSAPrivateCrtKey) pk;
        BigInteger n = privKey.getModulus();
        BigInteger e = privKey.getPublicExponent();
        BigInteger d = privKey.getPrivateExponent();
        BigInteger p = privKey.getPrimeP();
        BigInteger q = privKey.getPrimeQ();
        BigInteger dp = privKey.getPrimeExponentP();
        BigInteger dq = privKey.getPrimeExponentQ();
        BigInteger inverseQ = privKey.getCrtCoefficient();

        StringBuilder builder = new StringBuilder();
        builder.append("<RSAKeyValue>\n");
        write(builder, "Modulus", n);
        write(builder, "Exponent", e);
        write(builder, "P", p);
        write(builder, "Q", q);
        write(builder, "DP", dp);
        write(builder, "DQ", dq);
        write(builder, "InverseQ", inverseQ);
        write(builder, "D", d);
        builder.append("</RSAKeyValue>");
        return builder.toString();
    }
    private static void write(StringBuilder builder, String tag, BigInteger bigInt) throws Exception{
        builder.append("\t<");
        builder.append(tag);
        builder.append(">");
        builder.append(encode(bigInt));
        builder.append("</");
        builder.append(tag);
        builder.append(">\n");
    }
    private static String encode(BigInteger bigInt) throws Exception {
        return new String(Base64.encodeInteger(bigInt), "ASCII");
    }
    private static PrivateKey getPrivateKey(byte[] keyBytes) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}