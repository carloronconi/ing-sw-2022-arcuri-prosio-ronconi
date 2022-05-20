package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.GenericEvent;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public interface ViewEvent extends GenericEvent {
    void processMessage(VirtualView virtualView) throws InvalidObjectException, InterruptedException;
}
