package it.polimi.ingsw.networkmessages.viewevents;

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
}
