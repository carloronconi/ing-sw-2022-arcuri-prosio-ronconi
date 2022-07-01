package it.polimi.ingsw.networkmessages.modelevents;

import it.polimi.ingsw.networkmessages.ReceivedByClient;
import it.polimi.ingsw.utilities.EventManager;
import it.polimi.ingsw.utilities.ViewInterface;

import java.io.Serializable;

public class ServerHeartbeat implements ReceivedByClient, Serializable {
    @Override
    public void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager) {

    }
}
