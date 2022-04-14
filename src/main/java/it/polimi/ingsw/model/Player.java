package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;

import java.util.UUID;

public class Player {
    /*se metto final per Entrance e DiningRoom non posso usare il costruttore Player() in GameModel perché altrimenti
    non potrei ppiù aggiungere player dopo ma dovrei inserirli direttamente quando chiamo il costruttore di GameModel
    all'interno del quale dovrei chiamare il costruttore Player(Entrance, DiningRoom)*/
    private Entrance entrance;
    private DiningRoom diningRoom;
    private UUID id;

    public Player(){
        this.id=UUID.randomUUID();
    }

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

    public UUID getId(){ return id; }
}
