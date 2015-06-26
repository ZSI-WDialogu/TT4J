package TT4J;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by stokowiec on 2015-06-19.
 */
public class Event<T> {

    private List<Consumer<T>> subscribers;

    public  Event(){
        subscribers = new ArrayList<>();
    }

    void register(Consumer<T> consumer){
        subscribers.add(consumer);
    }

    void remove(Consumer<T> consumer){
        subscribers.remove(consumer);
    }

    public void invoke(T data){
        for(Consumer<T> consumer: subscribers){
            consumer.accept(data);
        }
    }
}
