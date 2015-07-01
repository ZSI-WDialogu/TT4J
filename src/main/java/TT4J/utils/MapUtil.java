package TT4J.utils;

import java.util.*;

public class MapUtil
{
    /**
     * Helper method to sort dictionary
     * @param map map to be sorted by Value
     * @return sorted map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
        List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );

        Collections.sort(list, (o1, o2) ->  o1.getValue().compareTo( o2.getValue()));

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }

        return result;
    }
}