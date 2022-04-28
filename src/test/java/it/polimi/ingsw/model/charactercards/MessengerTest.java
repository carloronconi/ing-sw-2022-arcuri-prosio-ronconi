package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessengerTest {
    private Messenger messenger;


    @Before
    public void setUp() {
        GameModel gameModel = null;
        messenger = new Messenger(gameModel);

        assertEquals(1, messenger.getCost());
        assertFalse(messenger.isCostIncreased());

    }

    @Test
    public void useEffect() {
        messenger.useEffect();

        assertTrue(messenger.isCostIncreased());

    }





}