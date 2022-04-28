package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.IslandManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WitchTest {
    private IslandManager islandManager;
    private Witch witch;

    @Before
    public void setUp(){
        Bag bag = new Bag();
        ProfessorManager professorManager = new ProfessorManager();
        islandManager = new IslandManager(bag, professorManager);
        witch = new Witch(islandManager);

        assertEquals(2, witch.getCost());
        assertFalse(witch.isCostIncreased());

        for(int i =0; i< islandManager.countIslands(); i++){
            assertFalse(islandManager.getIsland(i).ban);
        }

    }

    @Test
    public void useEffect() throws NoSuchFieldException {
        witch.useEffect(islandManager.getIsland(2).getId());
        assertTrue(islandManager.getIsland(2).ban);
        assertTrue(witch.isCostIncreased());
        assertEquals(3, witch.getCost());
    }


    @Test (expected = IllegalStateException.class)
    public void useEffect2() throws NoSuchFieldException{
        witch.useEffect(islandManager.getIsland(3).getId());
        witch.useEffect(islandManager.getIsland(4).getId());
        witch.useEffect(islandManager.getIsland(5).getId());
        witch.useEffect(islandManager.getIsland(7).getId());
        witch.useEffect(islandManager.getIsland(8).getId());
    }


    @Test
    public void increaseAvailableBans() throws NoSuchFieldException {
        islandManager.moveMotherNature(2);
        assertFalse(islandManager.getIsland(2).ban);

    }


    @Test (expected = IllegalArgumentException.class)
    public void increaseAvailableBans2(){
        IslandManager islandManager = null;
        witch.increaseAvailableBans(islandManager);
    }


}