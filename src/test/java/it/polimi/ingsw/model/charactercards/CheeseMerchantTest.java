package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import org.junit.Before;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CheeseMerchantTest {
    private CheeseMerchant cheeseMerchant;

    @Before
    public void setUp(){
        GameModel gameModel = null;
        cheeseMerchant = new CheeseMerchant(gameModel);

        assertEquals(2, cheeseMerchant.getCost());
        assertFalse(cheeseMerchant.isCostIncreased());
    }


}