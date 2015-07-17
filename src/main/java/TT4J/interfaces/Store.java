package TT4J.interfaces;

import java.io.IOException;

/**
 * Created by stokowiec on 2015-07-17.
 */
public interface Store {
    /**
     * Interface used in LinkProvider which should be implemented
     * by any class that generates URI of the active link
     * @param activeLink active link to be stored
     * @return  URI of the active link
     * @throws IOException
     */
    String storeLink(String activeLink) throws IOException;
}
