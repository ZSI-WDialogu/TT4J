package teamtalk.packets;


import teamtalk.enums.APINetworkPacketType;

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

    public APINetworkPacketType type() {
        return commandName;
    }

    public String getValue(String key){
        return params.getOrDefault(key, null);
    }

    @Override
    public String toString(){

        List<String> builder = new ArrayList<>();
        for(String key: params.keySet()){
            builder.add(String.format("\"%s\" : %s", key, params.get(key)));
        }

        return String.format("{ %s }", String.join(",", builder));
    }
}
