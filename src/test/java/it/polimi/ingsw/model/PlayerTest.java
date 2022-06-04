package it.polimi.ingsw.model;

import com.sun.jdi.event.ClassPrepareEvent;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void playAssistantCard(){
        assertEquals(10, player.getDeckSize());

        player.playAssistantCard(5);
        player.playAssistantCard(4);

        assertEquals(8, player.getDeckSize());
    }

    @Test
    public void coins(){
        assertEquals(1, player.getNumOfCoins());

        player.payCoins(1);

        assertEquals(0, player.getNumOfCoins());

    }

    @Test
    public void print(){
        String s = player.toString();

        assertEquals(s, player.toString());

    }

}