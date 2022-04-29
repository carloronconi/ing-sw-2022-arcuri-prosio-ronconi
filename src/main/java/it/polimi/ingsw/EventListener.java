package it.polimi.ingsw;

import it.polimi.ingsw.networkmessages.GenericEvent;

import java.io.InvalidObjectException;

/**
 * interface to be implemented by the listeners of a certain type of event
 * @param <EventType> enum describing the events that the listener can listen to
 */
public interface EventListener<EventType extends GenericEvent> {
    /**
     * method called by the publisher, the listener implements the method to react to an event
     * @param eventType type of the event to react to
     * @throws InvalidObjectException if the data of the event is invalid
     */
    void update(EventType eventType) throws InvalidObjectException;
}
