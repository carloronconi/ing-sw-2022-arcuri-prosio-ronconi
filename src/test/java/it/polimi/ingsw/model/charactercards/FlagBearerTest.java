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
    private Bag bag;
    private FlagBearer flagBearer;
    private ProfessorManager professorManager;
    private Player player;
    private Entrance entrance;
    private DiningRoom diningRoom;
    private final ArrayList<Cloud> clouds = new ArrayList<>();

    @Before
    public void setUp() throws NoSuchFieldException {
        bag = new Bag();
        professorManager = new ProfessorManager();
        islandManager = new IslandManager(bag, professorManager);
        flagBearer = new FlagBearer(islandManager);
        entrance = new Entrance(bag, clouds);
        diningRoom = new DiningRoom(entrance);
        player = new Player(entrance, diningRoom);



        assertEquals(3, flagBearer.getCost());
        assertFalse(flagBearer.isCostIncreased());
        assertEquals(113, bag.count());

        for(PawnColor color : PawnColor.values()){
            professorManager.setProfessorOwner(color, player);
        }

        UUID MNPositioon = islandManager.moveMotherNature(0);
        assertSame(MNPositioon, islandManager.getIsland(0).getId());

    }

    @Test
    public void useEffect() throws NoSuchFieldException {

        flagBearer.useEffect(islandManager.getIsland(8).getId());
        assertSame(player, islandManager.getIsland(8).getOwner());
        assertEquals(12, islandManager.countIslands());
        assertTrue(flagBearer.isCostIncreased());
        assertEquals(4, flagBearer.getCost());



        UUID MNPosition = islandManager.moveMotherNature(2);
        assertSame(MNPosition, islandManager.getIsland(2).getId());

    }

}