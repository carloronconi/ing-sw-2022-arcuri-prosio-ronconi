package it.polimi.ingsw.networkmessages.modelevents;

import it.polimi.ingsw.utilities.EventManager;
import it.polimi.ingsw.utilities.ViewInterface;
import it.polimi.ingsw.networkmessages.ReceivedByClient;

public interface ModelEvent extends ReceivedByClient {
    void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager);
}
