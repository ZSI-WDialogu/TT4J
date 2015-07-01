package TT4J.packets;


import TT4J.enums.APINetworkPacketType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by stokowiec on 2015-06-18.
 */
public class RawPacket {
    private APINetworkPacketType commandName;
    private Map<String, String> params;

    /**
     * Class used packet transformation.
     *
     *  Each packet is send as as string with specified formatting.
     * In an effort to deserialize packet efficiently, each
     * packet is transform to JSON-like string which is then used by JSON Deserializer
     *
     * @param response, string with response name and pairs of data
     */
    public RawPacket(String response){
        String[] split = response.split(" ", 2);
        commandName = APINetworkPacketType.fromString(split[0]);
        params = new HashMap<>();

        if(split.length > 1){
            Pattern p = Pattern.compile("(\\w*)=(\"[^\"]*\"|[^\\s]*)");
            Matcher m = p.matcher(split[1]);
            while(m.find()){
                String key = m.group(1);
                String value = m.group(2);
                params.put(key, value);
            }
        }
    }

    /**
     * @return APINetworkPacketType of the raw packet
     */
    public APINetworkPacketType type() {
        return commandName;
    }

    /**
     * Accessor method to get values of specified parameters
     * @param key, parameter name which value we would like to have
     * @return String value of requested parameter, or empty string if no such parameter exists
     */
    public String getValue(String key){
        return params.getOrDefault(key, null);
    }

    /**
     * To efficiently transform RawPacket to appropriate APINetworkPacket we take advantage of JSON
     *
     * @return String transformed to JSON deserializable format
     */
    @Override
    public String toString(){

        List<String> builder = new ArrayList<>();
        for(String key: params.keySet()){
            if(key.equals("audiocodec")){
                builder.add(String.format("\"%s\" : \"%s\"", key, params.get(key)));
            } else {
                builder.add(String.format("\"%s\" : %s", key, params.get(key)));
            }
        }

        return String.format("{ %s }", String.join(",", builder));
    }
}
