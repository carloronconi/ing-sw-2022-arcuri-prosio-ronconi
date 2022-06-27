package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.CheeseMerchant;
import it.polimi.ingsw.utilities.EventManager;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CheeseMerchantTest {
    private CheeseMerchant cheeseMerchant;
    private ArrayList<Player> players;

    @Before
    public void setUp(){
        ArrayList<String> playerNicknames = new ArrayList<>();
        playerNicknames.add("pippo");
        playerNicknames.add("pluto");

        EventManager<ModelEvent> eventManager = new EventManager<>();

        GameModel gameModel = new GameModel(false, playerNicknames, eventManager);
        cheeseMerchant = new CheeseMerchant(gameModel);

        assertEquals(2, cheeseMerchant.getCurrentCost());
        assertFalse(cheeseMerchant.isCostIncreased());
        assertEquals(AvailableCharacter.CHEESEMERCHANT, cheeseMerchant.getValue());

        players = gameModel.getPlayers();
    }

    /**
     * this method verifies that the chesseMerchant effect works correctly
     */
    @Test
    public void useEffect(){
        cheeseMerchant.setEffectPlayer(players.get(0).getId());
        try {
            cheeseMerchant.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }

        assertTrue(cheeseMerchant.isCostIncreased());
    }

    /**
     * this method verifies that the exception is thrown when the player is not set
     */
    @Test (expected = IllegalStateException.class)
    public void useEffect2(){
        try {
            cheeseMerchant.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }
    }


}