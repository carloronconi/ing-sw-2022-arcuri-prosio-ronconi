package it.polimi.ingsw.networkmessages.modelevents;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.GenericEvent;

public interface ModelEvent extends GenericEvent {
    void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager);
}
