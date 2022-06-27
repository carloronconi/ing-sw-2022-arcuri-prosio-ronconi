package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.Princess;
import it.polimi.ingsw.server.model.studentmanagers.Bag;
import it.polimi.ingsw.server.model.studentmanagers.Cloud;
import it.polimi.ingsw.server.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.server.model.studentmanagers.Entrance;
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

        assertEquals(2, princess.getCurrentCost());
        assertFalse(princess.isCostIncreased());
        assertEquals(119, bag.count());
        assertEquals(AvailableCharacter.PRINCESS, princess.getValue());

    }

    /**
     * this method tests that the princess effect works correctly
     */
    @Test
    public void useEffect() {


        for(PawnColor color : PawnColor.values()){
            if(princess.isColorContained(color)){
                int numStudents=player.getDiningRoom().count(color);
                princess.setEffectColor(color);
                princess.setEffectPlayer(player.getId());
                try {
                    princess.useEffect();
                } catch (NoSuchFieldException e) {
                    fail();
                }
                assertEquals(118, bag.count());
                assertEquals(numStudents+1, player.getDiningRoom().count(color));
                break;
            }
        }

    }

    /**
     * this method tests that the color of the student and
     * the player who will benefit from this effect have not been set
     */
    @Test (expected = IllegalStateException.class)
    public void useEffect2(){
        for(PawnColor color : PawnColor.values()){
            if(princess.isColorContained(color)){
                int numStudents=player.getDiningRoom().count(color);
                try {
                    princess.useEffect();
                } catch (NoSuchFieldException e) {
                    fail();
                }
                assertEquals(118, bag.count());
                assertEquals(numStudents+1, player.getDiningRoom().count(color));
                break;
            }
        }
    }

}