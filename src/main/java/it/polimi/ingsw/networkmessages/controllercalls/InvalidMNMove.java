package it.polimi.ingsw.networkmessages.controllercalls;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

import java.io.Serializable;

public class InvalidMNMove implements Serializable, RemoteMethodCall {
    @Override
    public void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager) {
        viewInterface.invalidMNMove();
    }
}
