package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;
import it.polimi.ingsw.model.studentmanagers.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {
    private Knight knight;
    private Player player;
    List<Player> players;

    @Before
    public void setUp(){
        Bag bag = new Bag();
        ProfessorManager professorManager = new ProfessorManager();
        IslandManager islandManager = new IslandManager(bag, professorManager);
        players = new ArrayList<>();
        List<Cloud> clouds = new ArrayList<>();
        Entrance entrance = new Entrance(bag, clouds);
        DiningRoom diningRoom = new DiningRoom(entrance);
        player = new Player(entrance, diningRoom);
        players.add(player);
        knight = new Knight(islandManager, players);

        assertEquals(2, knight.getCost());
        assertFalse(knight.isCostIncreased());
    }

    @Test
    public void useEffect() throws NoSuchFieldException {
        knight.useEffect(player.getId());

        assertTrue(knight.isCostIncreased());
    }

}