package it.polimi.ingsw.networkmessages;

import java.io.Serializable;

public class MovedMotherNature implements Serializable, ViewEvent {
    private final int motherNatureSteps;

    public MovedMotherNature(int motherNatureSteps){
        this.motherNatureSteps = motherNatureSteps;
    }

    public int getMotherNatureSteps(){
        return motherNatureSteps;
    }
}
