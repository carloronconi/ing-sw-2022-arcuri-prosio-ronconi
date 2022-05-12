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
        assertEquals(AvailableCharacter.WITCH, witch.getValue());

        for(int i =0; i< islandManager.countIslands(); i++){
            assertFalse(islandManager.getIsland(i).ban);
        }

    }

    /**
     * this method verifies that the witch effect works correctly
     */
    @Test
    public void useEffect(){
        witch.setEffectIsland(islandManager.getIsland(2).getId());
        try {
            witch.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
        assertTrue(islandManager.getIsland(2).ban);
        assertTrue(witch.isCostIncreased());
        assertEquals(3, witch.getCost());
    }


    /**
     * this method tests that the maximum number of bans that can be assigned is 4
     */
    @Test (expected = IllegalStateException.class)
    public void useEffect2(){
        witch.setEffectIsland(islandManager.getIsland(3).getId());
        try {
            witch.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
        witch.setEffectIsland(islandManager.getIsland(4).getId());
        try {
            witch.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
        witch.setEffectIsland(islandManager.getIsland(5).getId());
        try {
            witch.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
        witch.setEffectIsland(islandManager.getIsland(7).getId());
        try {
            witch.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
        witch.setEffectIsland(islandManager.getIsland(8).getId());
        try {
            witch.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * this method tests that the exception is thrown when the island to which the ban is to be assigned is not set
     */
    @Test (expected = IllegalStateException.class)
    public void useEffect3() {
        try {
            witch.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
    }


    /**
     * this method increases bans after motherNature arrives on the island which owns one
     */
    @Test
    public void increaseAvailableBans(){
        witch.setEffectIsland(islandManager.getIsland(8).getId());
        try {
            witch.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }

        assertTrue(islandManager.getIsland(8).ban);


        try {
            islandManager.moveMotherNature(8);
        } catch (NoSuchFieldException e) {
            fail();
        }
        assertFalse(islandManager.getIsland(8).ban);

    }

    /**
     * this method throws an exception when the island passed as a parameter is not the correct one
     */
    @Test (expected = IllegalArgumentException.class)
    public void increaseAvailableBans2(){
        IslandManager islandManager = null;
        witch.increaseAvailableBans(islandManager);
    }


}