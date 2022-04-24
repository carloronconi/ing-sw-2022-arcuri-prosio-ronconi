package it.polimi.ingsw;

import java.util.*;

public class EventManager<EventType extends Enum> {
    private final Map<EventType, List<EventListener>> listeners;

    public EventManager(Class<EventType> eventTypeClass){
        listeners = new HashMap<>();
        for (EventType eventType : eventTypeClass.getEnumConstants()){
            listeners.put(eventType, new ArrayList<>());
        }
    }

    public void subscribe(EventType type, EventListener listener){
        List<EventListener> subscribers = listeners.get(type);
        subscribers.add(listener);
    }

    public void unsubscribe(EventType type, EventListener listener){
        List<EventListener> subscribers = listeners.get(type);
        subscribers.remove(listener);
    }

    public void notify(EventType type, String data){
        List<EventListener> subscribers = listeners.get(type);
        for (EventListener s : subscribers){
            s.update(type, data);
        }
    }

}
