package it.polimi.ingsw;

import it.polimi.ingsw.networkmessages.GenericEvent;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.util.*;

/**
 * Use to create an eventManager to implement the observable-observer design pattern
 * @param <EventType> enum of types of event that the event manager will handle
 */
public class EventManager<EventType extends GenericEvent> {
    /**
     * maps listeners of each event type
     */
    private final List<EventListener<EventType>> listeners = new ArrayList<>();

    /**
     * add a listener to the event of one of the types described by the enum
     * @param listener to be subscribed to the type
     */
    public void subscribe(EventListener<EventType> listener){
        if (!listeners.contains(listener)) listeners.add(listener);
    }

    /**
     * remove a listener from a type of event
     * @param listener to be unsubscribed from the type
     */
    public void unsubscribe(EventListener<EventType> listener){
        listeners.remove(listener);
    }

    /**
     * to notify the subscriber of a certain type of event that a change has happened in the class containing the event manager
     * @param event of event that happened
     */
    public void notify(EventType event) {
        for (EventListener<EventType> l : listeners){
            l.update(event);
        }
    }

}
