package TT4J.utils;

import TT4J.utils.encryption.PrivateKeyHelper;
import TT4J.utils.encryption.PublicKeyHelper;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Properties;

/**
 * Created by stokowiec on 2015-06-26.
 */

/**
 * Helper class used to load configuration from file
 */
public class ConfigurationLoader {

    private String filePath;
    private String hostName;
    private int port;
    private boolean encrypted;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public ConfigurationLoader(String filePath){
        this.filePath = filePath;
        loadFromFile();
    }

    private void loadFromFile()  {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            ClassLoader classLoader = getClass().getClassLoader();
            input = classLoader.getResourceAsStream(filePath);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            this.hostName = prop.getProperty("hostname");
            this.port =  Integer.valueOf(prop.getProperty("port"));
            this.encrypted = Boolean.valueOf(prop.getProperty("encrypted"));


            this.privateKey = PrivateKeyHelper.read(
                    classLoader.getResourceAsStream("keys\\private.key"));

            this.publicKey = PublicKeyHelper.read(
                    classLoader.getResourceAsStream("keys\\public.key"));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
