package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.networkmessages.GenericEvent;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;

public interface ViewEvent extends GenericEvent {
    void processMessage(VirtualView virtualView) throws InvalidObjectException, InterruptedException;
}
