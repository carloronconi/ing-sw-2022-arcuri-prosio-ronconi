package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.ProfessorManager;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.MushroomMerchant;
import it.polimi.ingsw.server.model.studentmanagers.Bag;
import it.polimi.ingsw.server.model.studentmanagers.IslandManager;
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

        assertEquals(3, mushroomMerchant.getCurrentCost());
        assertFalse(mushroomMerchant.isCostIncreased());
        assertEquals(AvailableCharacter.MUSHROOMMERCHANT, mushroomMerchant.getValue());
    }

    /**
     * this method tests that the mushroomMerchant effect works correctly
     */
    @Test
    public void useEffect(){
        mushroomMerchant.setEffectColor(PawnColor.RED);
        mushroomMerchant.useEffect();

        assertTrue(mushroomMerchant.isCostIncreased());
    }

    /**
     * this method verifies that the exception is thrown if the color that should not provide influence
     * during that turn is not set
     */
    @Test (expected = IllegalStateException.class)
    public void useEffect2(){
        mushroomMerchant.useEffect();

    }
}