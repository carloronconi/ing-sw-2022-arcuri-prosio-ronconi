package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class PlayerTest {
    private Entrance entrance;
    private DiningRoom diningRoom;
    private Player player;
    private Bag bag;
    private List<Cloud> clouds;
    private ArrayList<Integer> assistantDeck;
    private int size;

    @BeforeEach
    void setup(){
        bag = new Bag();
        entrance = new Entrance(bag,clouds);
        diningRoom = new DiningRoom(entrance);
        player = new Player(entrance, diningRoom);
        this.assistantDeck = new ArrayList<>();
        for(int i =0; i<10; i++){
            this.assistantDeck.add(i+1);
        }


    }

    @Test
    void getDeckSize(){
    }

}
