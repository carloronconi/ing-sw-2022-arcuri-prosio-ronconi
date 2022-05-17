package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.UUID;

public class SetIslandChoice implements Serializable, GameViewEvent{
    private final UUID island;

    public SetIslandChoice(UUID island) {
        this.island = island;
    }

    public UUID getIsland() {
        return island;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {

    }
}
