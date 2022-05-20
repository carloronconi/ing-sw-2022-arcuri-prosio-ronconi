package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.UUID;

public class ChosenCloud implements Serializable, GameViewEvent {
    private final UUID cloud;

    public ChosenCloud(UUID cloud){
        this.cloud = cloud;
    }
    public UUID getCloud() {
        return cloud;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {

    }
}
