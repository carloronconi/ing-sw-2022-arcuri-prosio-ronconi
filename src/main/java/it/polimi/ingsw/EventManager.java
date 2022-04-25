package it.polimi.ingsw;

import java.io.InvalidObjectException;
import java.util.*;

public class EventManager<EventType extends Enum<EventType>> {
    private final Map<EventType, List<EventListener<EventType>>> listeners;

    public EventManager(Class<EventType> eventTypeClass){
        listeners = new HashMap<>();
        for (EventType eventType : eventTypeClass.getEnumConstants()){
            listeners.put(eventType, new ArrayList<>());
        }
    }

    public void subscribe(EventType type, EventListener<EventType> listener){
        List<EventListener<EventType>> subscribers = listeners.get(type);
        subscribers.add(listener);
    }

    public void unsubscribe(EventType type, EventListener<EventType> listener){
        List<EventListener<EventType>> subscribers = listeners.get(type);
        subscribers.remove(listener);
    }

    public void notify(EventType type, String data) throws InvalidObjectException {
        List<EventListener<EventType>> subscribers = listeners.get(type);
        for (EventListener<EventType> s : subscribers){
            s.update(type, data);
        }
    }

}
