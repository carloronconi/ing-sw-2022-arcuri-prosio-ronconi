package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.Juggler;
import it.polimi.ingsw.server.model.studentmanagers.Bag;
import it.polimi.ingsw.server.model.studentmanagers.Cloud;
import it.polimi.ingsw.server.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.server.model.studentmanagers.Entrance;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JugglerTest {
    private Player player;
    private Juggler juggler;
    private PawnColor colorTake;
    private PawnColor colorGive;
    private int numStudentsTake;
    private int numStudentsGive;


    @Before
    public void setUp(){
        Bag bag = new Bag();
        List<Cloud> clouds = new ArrayList<>();
        Entrance entrance = new Entrance(bag, clouds, 7);
        DiningRoom diningRoom = new DiningRoom(entrance);
        player = new Player(entrance, diningRoom, "testname");
        List<Player> players = new ArrayList<>();
        players.add(player);

        juggler = new Juggler(bag, players, null);

        assertEquals(1, juggler.getCurrentCost());
        assertFalse(juggler.isCostIncreased());
        assertEquals(117, bag.count());
        assertEquals(AvailableCharacter.JUGGLER, juggler.getValue());
        assertEquals(3, Juggler.getMaxColorSwaps());

        for(PawnColor c : PawnColor.values()){
            if(juggler.isColorContained(c)){
                colorTake=c;
                numStudentsTake=player.getEntrance().count(colorTake);
                break;
            }

        }

        for(PawnColor c : PawnColor.values()){
            if(player.getEntrance().count(c)>0 && colorTake!=c){
                colorGive=c;
                numStudentsGive=player.getEntrance().count(colorGive);
                break;
            }

        }

        juggler.setupColorSwaps(colorGive, colorTake);

    }

    /**
     * this method tests that the juggler effect works correctly
     */
    @Test
    public void useEffect() {
        juggler.setEffectPlayer(player.getId());

        try {
            juggler.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }

        assertEquals(2, juggler.getCurrentCost());
        assertEquals(numStudentsTake+1, player.getEntrance().count(colorTake));
        assertEquals(numStudentsGive-1, player.getEntrance().count(colorGive));
        assertTrue(juggler.isCostIncreased());
    }

}