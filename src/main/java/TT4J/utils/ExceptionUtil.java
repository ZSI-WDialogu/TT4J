package TT4J.utils;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class ExceptionUtil {

    /**
     * Helper class to rise exceptions if tested object is null
     * @param o object to be tested
     * @param name object name which should be substituted to error message
     */
    public static void require(Object o, String name){
        if(o==null)
            throw new IllegalArgumentException(String.format("%s cannot be null", name));
    }
}
