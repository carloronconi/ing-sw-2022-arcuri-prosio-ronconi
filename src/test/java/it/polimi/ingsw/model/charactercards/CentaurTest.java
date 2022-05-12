package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.IslandManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CentaurTest {
    private Centaur centaur;

    @Before
    public void setUp(){
        Bag bag = new Bag();
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(bag, professorManager);
        centaur = new Centaur(islandManager);

        assertEquals(3, centaur.getCost());
        assertFalse(centaur.isCostIncreased());
        assertEquals(AvailableCharacter.CENTAUR, centaur.getValue());
    }

    /**
     * this method tests that the centaur effect works correctly
     */
    @Test
    public void useEffect(){
        centaur.useEffect();
        assertTrue(centaur.isCostIncreased());
    }



}