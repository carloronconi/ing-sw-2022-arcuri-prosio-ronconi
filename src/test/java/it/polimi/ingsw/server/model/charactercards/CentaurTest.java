package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.ProfessorManager;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.Centaur;
import it.polimi.ingsw.server.model.studentmanagers.Bag;
import it.polimi.ingsw.server.model.studentmanagers.IslandManager;
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

        assertEquals(3, centaur.getCurrentCost());
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