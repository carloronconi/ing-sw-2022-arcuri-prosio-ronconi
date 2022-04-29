package it.polimi.ingsw.networkmessages;

import it.polimi.ingsw.model.studentmanagers.Cloud;

import java.io.Serializable;
import java.util.UUID;

public class ChosenCloud implements Serializable {
    private final UUID cloud;

    public ChosenCloud(UUID cloud){
        this.cloud = cloud;
    }
    public UUID getCloud() {
        return cloud;
    }
}
