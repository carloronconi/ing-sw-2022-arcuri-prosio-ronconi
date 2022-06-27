package it.polimi.ingsw.networkmessages.controllercalls;


import it.polimi.ingsw.utilities.EventManager;
import it.polimi.ingsw.utilities.ViewInterface;
import it.polimi.ingsw.networkmessages.ReceivedByClient;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

public interface RemoteMethodCall extends ReceivedByClient {
    /**
     * process the message using the real view of the client that received the message
     */
    void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager);
}
