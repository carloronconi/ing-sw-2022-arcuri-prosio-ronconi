package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;

public class DiningRoom extends StudentCounter {
    private final Entrance entrance;

    public DiningRoom(Entrance entrance) {
        super();
        this.entrance = entrance;
    }

    public void fill(PawnColor color){
        movePawnFrom(entrance,color);
    }
}
