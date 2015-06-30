package TT4J.utils;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Created by Stokowiec on 2015-06-30.
 */
public class CollectionUtils {

    public static <T> T getFirst(Collection<T> collection, Predicate<T> pred){
        T res = null;
        for(T t: collection){
            if(pred.test(t)) res = t;
        }

        ExceptionUtil.require(res, "Element");

        return res;
    }
}
