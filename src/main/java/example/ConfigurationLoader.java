package example;

import java.io.*;
import java.util.Properties;

/**
 * Created by stokowiec on 2015-06-26.
 */
public class ConfigurationLoader {

    private String filePath;

    private String hostName;
    private int port;

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

        } catch (IOException ex) {
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
}
