package it.polimi.ingsw.server.model.studentmanagers;

import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.studentmanagers.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EntranceTest extends StudentCounter {
    private ArrayList<Cloud> clouds;
    private Entrance entrance;
    private DiningRoom diningRoom;

    @Before
    public void setUp() {
        Bag bag = new Bag();
        Cloud cloud = new Cloud(bag);
        clouds = new ArrayList<>();
        clouds.add(cloud);

        cloud.fill(3);
        assertEquals(3, cloud.count());

        entrance = new Entrance(bag, clouds, 7);
        assertEquals(7, entrance.count());

        diningRoom = new DiningRoom(entrance);
        assertEquals(0, diningRoom.count());

    }

    /**
     * this method verifies that the filling of the entrance from the cloud works correctly
     */
    @Test
    public void fill() {

        int studentsNum = 0;
        for (PawnColor color : PawnColor.values()){
            while (entrance.count(color)>0 && studentsNum!=3){
                diningRoom.fill(color);
                studentsNum++;
            }
            if (studentsNum == 3){
                break;
            }
        }

        assertEquals(4, entrance.count());
        assertEquals(3, diningRoom.count());

        try {
            entrance.fill(clouds.get(0).getId());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assertEquals(0, clouds.get(0).count());
        assertEquals(7, entrance.count());
        assertEquals(3, diningRoom.count());

        }

    }



