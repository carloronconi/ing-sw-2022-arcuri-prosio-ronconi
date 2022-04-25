package it.polimi.ingsw;

import java.io.InvalidObjectException;

public interface EventListener<EventType extends Enum<EventType>> {
    void update(EventType eventType, String data) throws InvalidObjectException;
}
