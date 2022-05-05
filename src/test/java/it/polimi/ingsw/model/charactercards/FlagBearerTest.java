package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.studentmanagers.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
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



        assertEquals(3, flagBearer.getCost());
        assertFalse(flagBearer.isCostIncreased());
        assertEquals(113, bag.count());

        for(PawnColor color : PawnColor.values()){
            professorManager.setProfessorOwner(color, player);
        }
        UUID MNPosition = islandManager.getMotherNaturePosition();
        assertSame(MNPosition, islandManager.getIsland(0).getId());
        //assertEquals(MNPosition, islandManager.getIsland(0).getId()); da capire se va Same o Equals

    }

    @Test
    public void useEffect() throws NoSuchFieldException {
        flagBearer.setEffectIsland(islandManager.getIsland(8).getId());

        flagBearer.useEffect();
        assertSame(player, islandManager.getIsland(8).getOwner());
        assertEquals(12, islandManager.countIslands());
        assertTrue(flagBearer.isCostIncreased());
        assertEquals(4, flagBearer.getCost());

        islandManager.moveMotherNature(2);
        UUID MNPosition = islandManager.getMotherNaturePosition();
        assertSame(MNPosition, islandManager.getIsland(2).getId());

    }

}