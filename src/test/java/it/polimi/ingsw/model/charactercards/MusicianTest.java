package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MusicianTest {
    private Musician musician;
    private Entrance entrance;
    private DiningRoom diningRoom;

    @Before
    public void setUp(){
        Bag bag = new Bag();
        List<Cloud> clouds = new ArrayList<>();
        entrance = new Entrance(bag, clouds, 7);
        diningRoom = new DiningRoom(entrance);
        Player player = new Player(entrance, diningRoom, "testname");
        List<Player> players = new ArrayList<>();

        players.add(player);

        musician = new Musician(players);

        assertEquals(123, bag.count());
        assertEquals(1, players.size());
        assertEquals(1, musician.getCost());
        assertFalse(musician.isCostIncreased());


        /*
        ArrayList<PawnColor> movableColors = new ArrayList<>();
        for(PawnColor color : PawnColor.values()){
            if(player.getEntrance().get(color) > 0){
                movableColors.add(color);
            }
        }
        if(movableColors.size() == 0) throw new IllegalArgumentException();

        Random rand = new Random();
        int i = rand.nextInt(movableColors.size());
        PawnColor c = movableColors.get(i);
        movePawnFrom(other, c);
         */




        for(Player p : players){
            int numTransferedStudents=0;
            boolean numReached=false;
            for(PawnColor pawnColor : PawnColor.values()){
                if(p.getEntrance().count(pawnColor)>1 && numTransferedStudents<2){
                    p.getDiningRoom().fill(pawnColor);
                    numTransferedStudents++;
                }
            }
        }



    }

    @Test
    public void useEffect(){

    }

}