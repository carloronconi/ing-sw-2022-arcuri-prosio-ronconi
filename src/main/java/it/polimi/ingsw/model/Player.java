package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;

public class Player {
    private final Entrance entrance;
    private final DiningRoom diningRoom;

    public Player(Entrance entrance, DiningRoom diningRoom) {
        this.entrance = entrance;
        this.diningRoom = diningRoom;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }
}
