package it.polimi.ingsw.networkmessages.viewevents;

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
}
