package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.studentmanagers.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FlagBearerTest {
    private IslandManager islandManager;
    private FlagBearer flagBearer;
    private Player player;
    private final ArrayList<Cloud> clouds = new ArrayList<>();

    @Before
    public void setUp() {
        Bag bag = new Bag();
        ProfessorManager professorManager = new ProfessorManager();
        islandManager = new IslandManager(bag, professorManager);
        flagBearer = new FlagBearer(islandManager);
        Entrance entrance = new Entrance(bag, clouds, 7);
        DiningRoom diningRoom = new DiningRoom(entrance);
        player = new Player(entrance, diningRoom, "testNickname");



        assertEquals(3, flagBearer.getCurrentCost());
        assertFalse(flagBearer.isCostIncreased());
        assertEquals(113, bag.count());

        for(PawnColor color : PawnColor.values()){
            professorManager.setProfessorOwner(color, player);
        }
        UUID MNPosition = islandManager.getMotherNaturePosition();
        assertSame(MNPosition, islandManager.getIsland(0).getId());
        //assertEquals(MNPosition, islandManager.getIsland(0).getId()); da capire se va Same o Equals

    }

    /**
     * this method tests that the flagBearer effect works correctly
     */
    @Test
    public void useEffect() {
        flagBearer.setEffectIsland(islandManager.getIsland(8).getId());

        try {
            flagBearer.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
        assertSame(player, islandManager.getIsland(8).getOwner());
        assertEquals(12, islandManager.countIslands());
        assertTrue(flagBearer.isCostIncreased());
        assertEquals(4, flagBearer.getCurrentCost());

        try {
            islandManager.moveMotherNature(2);
        } catch (NoSuchFieldException e) {
            fail();
        }
        UUID MNPosition = islandManager.getMotherNaturePosition();
        assertSame(MNPosition, islandManager.getIsland(2).getId());

    }

    /**
     * this method verifies that the exception is thrown if the island on which to calculate the majority is not set
     */
    @Test (expected = IllegalStateException.class)
    public void useEffect2(){
        try {
            flagBearer.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
        assertSame(player, islandManager.getIsland(8).getOwner());
        assertEquals(12, islandManager.countIslands());
        assertTrue(flagBearer.isCostIncreased());
        assertEquals(4, flagBearer.getCurrentCost());

        try {
            islandManager.moveMotherNature(2);
        } catch (NoSuchFieldException e) {
            fail();
        }
        UUID MNPosition = islandManager.getMotherNaturePosition();
        assertSame(MNPosition, islandManager.getIsland(2).getId());
    }

}