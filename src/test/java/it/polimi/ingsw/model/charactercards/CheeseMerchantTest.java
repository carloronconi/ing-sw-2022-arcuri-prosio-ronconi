package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;

class CheeseMerchantTest {
    private CheeseMerchant cheeseMerchant;

    @Before
    public void setUp(){
        GameModel gameModel = new GameModel(true);
        cheeseMerchant = new CheeseMerchant(gameModel);

        assertEquals(2, cheeseMerchant.getCost());
        assertFalse(cheeseMerchant.isCostIncreased());
    }


}