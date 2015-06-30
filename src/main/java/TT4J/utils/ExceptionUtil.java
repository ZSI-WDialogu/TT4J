package TT4J.utils;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class ExceptionUtil {

    public static void require(Object o, String name){
        if(o==null)
            throw new IllegalArgumentException(String.format("%s cannot be null", name));
    }
}
