package it.polimi.ingsw.networkmessages;

import java.io.Serializable;

public class MovedMotherNature implements Serializable {
    private final int motherNatureSteps;

    public MovedMotherNature(int motherNatureSteps){
        this.motherNatureSteps = motherNatureSteps;
    }

    public int getMotherNatureSteps(){
        return motherNatureSteps;
    }
}
