package it.polimi.ingsw.networkmessages.controllercalls;


import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.ReceivedByClient;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.server.ClientHandler;

public interface RemoteMethodCall extends ReceivedByClient {
    /**
     * process the message using the real view of the client that received the message
     */
    void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager);
}
