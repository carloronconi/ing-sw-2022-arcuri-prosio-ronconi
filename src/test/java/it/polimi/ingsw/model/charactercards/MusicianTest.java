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

public class MusicianTest {
    private Musician musician;
    private Player player;
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

        musician = new Musician(players);

        assertEquals(123, bag.count());
        assertEquals(1, players.size());
        assertEquals(1, musician.getCost());
        assertFalse(musician.isCostIncreased());
        assertEquals(AvailableCharacter.MUSICIAN, musician.getValue());


        for(PawnColor c : PawnColor.values()){
            if(player.getEntrance().count(c)>0){
                player.getDiningRoom().fill(c);
                colorTake=c;
                numStudentsTake=player.getEntrance().count(c);
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

        musician.setupColorSwaps(colorGive, colorTake);

    }

    /**
     * this method tests that the musician effect works correctly
     */
    @Test
    public void useEffect(){
        musician.setEffectPlayer(player.getId());

        try {
            musician.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }

        assertEquals(2, musician.getCost());
        assertEquals(numStudentsTake+1, player.getEntrance().count(colorTake));
        assertEquals(numStudentsGive-1, player.getEntrance().count(colorGive));
        assertTrue(musician.isCostIncreased());
    }

}