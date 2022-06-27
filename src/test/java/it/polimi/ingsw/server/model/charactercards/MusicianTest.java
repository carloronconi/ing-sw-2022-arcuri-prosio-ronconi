package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.Musician;
import it.polimi.ingsw.utilities.EventManager;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MusicianTest {
    private Musician musician;
    private Player player;
    private PawnColor colorTake;
    private PawnColor colorGive;
    private int numStudentsTake;
    private int numStudentsGive;

    @Before
    public void setUp(){
        ArrayList<String> playerNicknames = new ArrayList<>();
        playerNicknames.add("pippo");
        playerNicknames.add("pluto");

        EventManager<ModelEvent> eventManager = new EventManager<>();

        GameModel gameModel =  new GameModel(false, playerNicknames, eventManager);

        List<Player> players = gameModel.getPlayers();
        player = players.get(0);

        musician = new Musician(players, gameModel);

        assertEquals(2, players.size());
        assertEquals(1, musician.getCurrentCost());
        assertFalse(musician.isCostIncreased());
        assertEquals(AvailableCharacter.MUSICIAN, musician.getValue());
        assertEquals(2, Musician.getMaxColorSwaps());


        for(PawnColor c : PawnColor.values()){
            if(player.getEntrance().count(c)>0){
                player.getDiningRoom().fill(c);
                colorTake=c;
                numStudentsTake=player.getEntrance().count(c);
                break;
            }
        }

        for(PawnColor c : PawnColor.values()){
            if(player.getEntrance().count(c)>0 && colorTake!=c){
                colorGive=c;
                numStudentsGive=player.getEntrance().count(colorGive);
                break;
            }
        }

        musician.setupColorSwaps(colorGive, colorTake);

    }

    /**
     * this method tests that the musician effect works correctly
     */
    @Test
    public void useEffect(){
        musician.setEffectPlayer(player.getId());

        try {
            musician.useEffect();
        } catch (NoSuchFieldException e) {
            fail();
        }

        assertEquals(2, musician.getCurrentCost());
        assertEquals(numStudentsTake+1, player.getEntrance().count(colorTake));
        assertEquals(numStudentsGive-1, player.getEntrance().count(colorGive));
        assertTrue(musician.isCostIncreased());
    }

}