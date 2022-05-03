package it.polimi.ingsw;

import it.polimi.ingsw.networkmessages.GenericEvent;

import java.io.InvalidObjectException;
import java.util.*;

/**
 * Use to create an eventManager to implement the observable-observer design pattern
 * @param <EventType> enum of types of event that the event manager will handòe
 */
public class EventManager<EventType extends GenericEvent> {
    /**
     * maps listeners of each event type
     */
    private final Map<Class<? extends EventType>, List<EventListener<EventType>>> listeners;

    /**
     * creates event manager for the given event type enum
     */
    public EventManager(){
        listeners = new HashMap<>();
    }

    /**
     * add a listener to the event of one of the types described by the enum
     * @param type event to subscribe to
     * @param listener to be subscribed to the type
     */
    public void subscribe(Class<? extends EventType> type, EventListener<EventType> listener){
        List<EventListener<EventType>> subscribers = listeners.get(type);
        if (subscribers==null) subscribers = new ArrayList<>();
        subscribers.add(listener);
        listeners.put(type, subscribers);
    }

    /**
     * remove a listener from a type of event
     * @param type of event to unsubscribe from
     * @param listener to be unsubscribed from the type
     */
    public void unsubscribe(Class<? extends EventType> type, EventListener<EventType> listener){
        List<EventListener<EventType>> subscribers = listeners.get(type);
        subscribers.remove(listener);
    }

    /**
     * to notify the subscriber of a certain type of event that a change has happened in the class containing the event manager
     * @param type of event that happened
     */
    public void notify(EventType type) {
        List<EventListener<EventType>> subscribers = listeners.get(type.getClass());
        for (EventListener<EventType> s : subscribers){
            s.update(type);
        }
    }

}
