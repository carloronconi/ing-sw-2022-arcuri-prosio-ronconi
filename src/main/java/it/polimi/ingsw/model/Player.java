package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;

import java.util.UUID;

public class Player {
    private final Entrance entrance;
    private final DiningRoom diningRoom;
    private final UUID id;

    public Player(Entrance entrance, DiningRoom diningRoom) {
        this.entrance = entrance;
        this.diningRoom = diningRoom;
        this.id=UUID.randomUUID();
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }
}
