package it.polimi.ingsw.networkmessages;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

public interface ReceivedByClient extends GenericEvent{
    void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager);
}
