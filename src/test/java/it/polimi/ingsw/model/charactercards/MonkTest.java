package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.IslandManager;
import it.polimi.ingsw.model.studentmanagers.IslandTile;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MonkTest {
    private Bag bag;
    private IslandManager islandManager;
    private Monk monk;
    IslandTile islandTile;

    @Before
    public void setUp(){
        bag = new Bag();
        islandManager = new IslandManager(bag);
        monk = new Monk(bag, islandManager);

        assertEquals(1, monk.getCost());
        assertFalse(monk.isCostIncreased());
        assertEquals(116, bag.count());
    }

    @Test
    public void useEffect() throws NoSuchFieldException {
        islandTile =  new IslandTile();
        islandTile = islandManager.getIsland(1);

        int numStudents=islandTile.count(PawnColor.RED);

        monk.useEffect(PawnColor.RED, islandTile.getId());

        assertEquals(115, bag.count());
        assertEquals(numStudents+1, islandTile.count(PawnColor.RED));
    }








}
