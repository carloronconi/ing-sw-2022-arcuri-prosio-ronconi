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

import static org.junit.jupiter.api.Assertions.*;

public class PrincessTest {
    private Bag bag;
    private Player player;
    private Princess princess;

    @Before
    public void setUp(){
        bag = new Bag();
        List<Player> players = new ArrayList<>();
        List<Cloud> clouds = new ArrayList<>();
        Entrance entrance = new Entrance(bag, clouds, 7);
        DiningRoom diningRoom = new DiningRoom(entrance);
        player = new Player(entrance, diningRoom, "testname");
        players.add(player);
        princess = new Princess(bag, players);

        assertEquals(2, princess.getCost());
        assertFalse(princess.isCostIncreased());
        assertEquals(119, bag.count());

    }

    @Test
    public void useEffect() throws NoSuchFieldException {


        for(PawnColor color : PawnColor.values()){
            if(princess.isColorContained(color)){
                int numStudents=player.getDiningRoom().count(color);
                princess.setEffectColor(color);
                princess.setEffectPlayer(player.getId());
                princess.useEffect();
                assertEquals(118, bag.count());
                assertEquals(numStudents+1, player.getDiningRoom().count(color));
                break;
            }
        }

    }

}