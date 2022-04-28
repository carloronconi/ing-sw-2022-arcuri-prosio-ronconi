package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.IslandManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MushroomMerchantTest {
    private MushroomMerchant mushroomMerchant;

    @Before
    public void setUp(){
        Bag bag = new Bag();
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(bag, professorManager);
        mushroomMerchant = new MushroomMerchant(islandManager);

        assertEquals(3, mushroomMerchant.getCost());
        assertFalse(mushroomMerchant.isCostIncreased());
    }

    @Test
    public void useEffect(){
        mushroomMerchant.useEffect(PawnColor.RED);

        assertTrue(mushroomMerchant.isCostIncreased());
    }

}