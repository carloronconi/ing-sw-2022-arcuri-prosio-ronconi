package it.polimi.ingsw.networkmessages.modelevents;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.GenericEvent;
import it.polimi.ingsw.networkmessages.ReceivedByClient;

public interface ModelEvent extends ReceivedByClient {
    void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager);
}
