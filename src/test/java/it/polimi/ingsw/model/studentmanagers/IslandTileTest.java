package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.Identifiable;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static it.polimi.ingsw.model.PawnColor.BLUE;
import static it.polimi.ingsw.model.PawnColor.RED;
import static org.junit.jupiter.api.Assertions.*;

class IslandTileTest extends StudentCounter {
    private Bag bag;
    private List<Cloud> clouds;
    private Entrance entrance;
    private IslandTile curr;
    private IslandTile next;
    int pawnCurr;
    int pawnNext;
    int pawnCurrAfter;
    private int size =1;

    private Player owner;
    private Player otherPlayer;
    private DiningRoom diningRoom;
    private ProfessorManager professorManager;
    private Player newOwner;

    @BeforeEach
    void setUp() {
        bag = new Bag();
        clouds = new ArrayList<>();
        IntStream.range(0,3).forEach(i -> clouds.add(new Cloud(bag)));

        entrance = new Entrance(bag,clouds);
        curr = new IslandTile();
        next = new IslandTile();
        pawnCurr = curr.count();
        pawnNext = next.count();

        //checkNewOwner setup
        diningRoom = new DiningRoom(entrance);
        owner = new Player(entrance, diningRoom);
        otherPlayer = new Player(entrance, diningRoom);
        professorManager = new ProfessorManager();
        professorManager.setProfessorOwner(BLUE, owner);
        professorManager.setProfessorOwner(RED, owner);
        professorManager.setProfessorOwner(PawnColor.GREEN, otherPlayer);
        newOwner = new Player(entrance, diningRoom);
    }

    //TODO: implement tests for all methods when IslandTile class completed

    @Test
    void  moveAllPawnsFrom(){
        size++;
        for (PawnColor c: PawnColor.values()) {
            for (int i = 0; i < next.count(c); i++) {
                curr.movePawnFrom(next,c);
                pawnCurrAfter = curr.count();
            }
        }

        assertEquals(2, size);
        assertEquals(pawnCurr+pawnNext, pawnCurrAfter);

    }

    @Test
    void checkNewOwner(){
       int green = curr.count(PawnColor.GREEN);
       int red = curr.count(RED);
       int blue = curr.count(BLUE);

       if(green> red+blue+size){
           newOwner = otherPlayer;
       }else{
           newOwner = owner;
       }

        assertEquals(owner, newOwner);



    }



}