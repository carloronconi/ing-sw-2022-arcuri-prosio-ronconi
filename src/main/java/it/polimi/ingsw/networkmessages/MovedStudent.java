package it.polimi.ingsw.networkmessages;

import it.polimi.ingsw.model.PawnColor;

import java.io.Serializable;
import java.util.UUID;

public class MovedStudent implements Serializable, ViewEvent {
    private final PawnColor color;
    private final UUID islandId;

    public MovedStudent(PawnColor color, UUID islandId){
        this.color=color;
        this.islandId = islandId; //null if the student has to be moved to the entrance
    }

    public PawnColor getColor(){
        return color;
    }

    public UUID getIslandId() {
        return islandId;
    }
}
