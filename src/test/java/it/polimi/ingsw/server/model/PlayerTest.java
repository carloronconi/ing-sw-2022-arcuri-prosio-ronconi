package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.studentmanagers.Bag;
import it.polimi.ingsw.server.model.studentmanagers.Cloud;
import it.polimi.ingsw.server.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.server.model.studentmanagers.Entrance;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp(){
        Bag bag = new Bag();
        ArrayList<Cloud> clouds = new ArrayList<>();
        Entrance entrance;

        PawnColor c = null;
        boolean colorFound = false;
        do {
            entrance = new Entrance(bag, clouds, 7);
            for (PawnColor color : PawnColor.values()) {
                if (entrance.count(color) > 2) {
                    colorFound = true;
                    c = color;
                    break;
                }
            }

        }while (!colorFound);


        DiningRoom diningRoom = new DiningRoom(entrance);

        player = new Player(entrance, diningRoom, "pippo");

        for (int i = 0; i<3; i++){
            player.moveStudentToDining(c, true);
        }

        assertEquals(10, player.getDeckSize());

    }

    /**
     * this method verifies that the playAssistantCard method works correctly
     */
    @Test
    public void playAssistantCard(){
        assertEquals(10, player.getDeckSize());

        player.playAssistantCard(5);
        player.playAssistantCard(4);

        assertEquals(8, player.getDeckSize());
    }

    /**
     * this method verifies that the payCoins method works correctly
     */
    @Test
    public void coins(){
        assertEquals(1, player.getNumOfCoins());

        player.payCoins(1);

        assertEquals(0, player.getNumOfCoins());

    }

    /**
     * this method verifies that printing is working correctly
     */
    @Test
    public void print(){
        String s = player.toString();

        assertEquals(s, player.toString());

    }

}