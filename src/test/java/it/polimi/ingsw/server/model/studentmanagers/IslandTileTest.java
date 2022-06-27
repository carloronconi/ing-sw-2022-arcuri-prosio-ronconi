package it.polimi.ingsw.server.model.studentmanagers;

import it.polimi.ingsw.server.model.ProfessorManager;
import it.polimi.ingsw.server.model.studentmanagers.Bag;
import it.polimi.ingsw.server.model.studentmanagers.IslandManager;
import it.polimi.ingsw.server.model.studentmanagers.IslandTile;
import it.polimi.ingsw.server.model.studentmanagers.StudentCounter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IslandTileTest extends StudentCounter {

    private IslandManager islandManager;

    @Before
    public void setUp() {
        Bag bag = new Bag();
        ProfessorManager professorManager = new ProfessorManager();
        islandManager = new IslandManager(bag, professorManager);

        assertEquals(12, islandManager.countIslands());
        for (IslandTile islandTile : islandManager.getIslands()){
            if (islandTile.equals(islandManager.getIsland(0)) || islandTile.equals(islandManager.getIsland(6))){
                assertEquals(0, islandTile.count());
            }else{
                assertEquals(1, islandTile.count());
            }
        }

    }

    /**
     * this method verifies that the moveAllPawnsFrom method works correctly
     */
    @Test
    public void moveAllPawnsFrom(){

        islandManager.getIsland(2).moveAllPawnsFrom(islandManager.getIsland(3));

        assertEquals(2, islandManager.getIsland(2).count());
        assertEquals(0, islandManager.getIsland(3).count());

    }



}