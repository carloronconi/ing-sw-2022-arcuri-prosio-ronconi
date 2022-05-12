package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MessengerTest {
    private EventManager<ModelEvent> eventManager;
    List<String> nicknames;

    @Before
    public void setUp(){
        String nickname1 = "pippo";
        String nickname2 = "pluto";

        nicknames = new ArrayList<>();
        nicknames.add(nickname1);
        nicknames.add(nickname2);

        eventManager = new EventManager<>();
        EventListener<ModelEvent> listener = modelEvent -> System.out.println("update received");
        eventManager.subscribe(GameState.class, listener);



        assertEquals(2, nicknames.size());

    }

    /**
     * this method tests that the messenger effect works correctly
     */
    @Test
    public void useEffect(){
        GameModel gameModel = new GameModel(true, nicknames, eventManager);

        while(!gameModel.getAvailableCharacterCards().keySet().contains(AvailableCharacter.MESSENGER)){
            gameModel = new GameModel(true, nicknames, eventManager);
        }

        Messenger messenger = new Messenger(gameModel);

        assertFalse(messenger.isCostIncreased());
        assertEquals(1, messenger.getCost());
        assertEquals(AvailableCharacter.MESSENGER, messenger.getValue());


        messenger.useEffect();

        assertTrue(messenger.isCostIncreased());
    }

    /*
    @Test (expected = IllegalStateException.class)
    public void useEffect2(){
        gameModel = new GameModel(true, nicknames, eventManager);

        while(gameModel.getAvailableCharacterCards().keySet().contains(AvailableCharacter.MESSENGER)){
            gameModel = new GameModel(true, nicknames, eventManager);
        }

        messenger = new Messenger(gameModel);

        assertFalse(messenger.isCostIncreased());
        assertEquals(1, messenger.getCost());
        assertEquals(AvailableCharacter.MESSENGER, messenger.getValue());


        messenger.useEffect();

    }
     */

}