package TT4J.interfaces;

import java.io.IOException;

/**
 * Created by stokowiec on 2015-07-17.
 */
public interface Store {
    String storeLink(String activeLink) throws IOException;
}
