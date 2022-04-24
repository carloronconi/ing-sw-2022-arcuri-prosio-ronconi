package it.polimi.ingsw;

public interface EventListener<EventType extends Enum> {
    void update(EventType eventType, String data);
}
