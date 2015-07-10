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
    private String ttHostName;
    private int ttPort;
    private boolean ttEncrypted;
    private String restHostName;
    private int restPort;

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

            // get configuration of Team Talk server
            this.ttHostName = prop.getProperty("tt4j-hostname");
            this.ttPort =  Integer.valueOf(prop.getProperty("tt4j-port"));
            this.ttEncrypted = Boolean.valueOf(prop.getProperty("tt4j-encrypted"));


            // get configuration of REST SERVER
            this.restHostName = prop.getProperty("rest-hostname");
            this.restPort =  Integer.valueOf(prop.getProperty("rest-port"));

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

    public String getTtHostName() {
        return ttHostName;
    }

    public int getTtPort() {
        return ttPort;
    }

    public boolean isTtEncrypted() {
        return ttEncrypted;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getRestHostName() {
        return restHostName;
    }

    public int getRestPort() {
        return restPort;
    }
}
