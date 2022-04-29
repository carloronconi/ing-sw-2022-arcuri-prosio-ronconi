package it.polimi.ingsw.networkmessages;

import it.polimi.ingsw.model.PawnColor;

public class MovedStudent {
    private final PawnColor color;

    public MovedStudent(PawnColor color){
        this.color=color;
    }

    public PawnColor getColor(){
        return color;
    }

}
