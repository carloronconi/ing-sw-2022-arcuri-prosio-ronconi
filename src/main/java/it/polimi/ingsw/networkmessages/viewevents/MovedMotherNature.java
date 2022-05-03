package it.polimi.ingsw.networkmessages.viewevents;

import java.io.Serializable;

public class MovedMotherNature implements Serializable, GameViewEvent {
    private final int motherNatureSteps;

    public MovedMotherNature(int motherNatureSteps){
        this.motherNatureSteps = motherNatureSteps;
    }

    public int getMotherNatureSteps(){
        return motherNatureSteps;
    }
}
