package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class Heartbeat implements Serializable, SetupViewEvent{
    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException, InterruptedException {

    }
}
