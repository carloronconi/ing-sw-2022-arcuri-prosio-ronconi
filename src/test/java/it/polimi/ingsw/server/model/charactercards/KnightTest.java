package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.ProfessorManager;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.Knight;
import it.polimi.ingsw.server.model.studentmanagers.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {
    private Knight knight;
    private Player player;

    @Before
    public void setUp(){
        Bag bag = new Bag();
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(bag, professorManager);
        List<Player> players = new ArrayList<>();
        List<Cloud> clouds = new ArrayList<>();
        Entrance entrance = new Entrance(bag, clouds, 7);
        DiningRoom diningRoom = new DiningRoom(entrance);
        player = new Player(entrance, diningRoom, "testname");
        players.add(player);
        knight = new Knight(islandManager, players);

        assertEquals(2, knight.getCurrentCost());
        assertFalse(knight.isCostIncreased());
        assertEquals(AvailableCharacter.KNIGHT, knight.getValue());
    }

    /**
     * this method tests that the knight effect works correctly
     */
    @Test
    public void useEffect() {
        knight.setEffectPlayer(player.getId());
        try {
            knight.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }

        assertTrue(knight.isCostIncreased());
    }

    /**
     * this method tests that the exception is thrown if the player who will benefit from the knight's effect is not set
     */
    @Test (expected = IllegalStateException.class)
    public void useEffect2(){
        try {
            knight.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

}