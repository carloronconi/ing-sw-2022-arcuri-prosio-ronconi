package it.polimi.ingsw.server.model.studentmanagers;

import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.studentmanagers.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;

public class DiningRoomTest extends StudentCounter {
    private Bag bag;
    private ArrayList<Cloud> clouds;
    private Entrance entrance;
    private DiningRoom diningRoom;

    @Before
    public void setup(){
        bag = new Bag();
        clouds = new ArrayList<>();
        IntStream.range(0, 3).forEach(i -> clouds.add(new Cloud(bag)));
        entrance = new Entrance(bag, clouds, 7);
        diningRoom = new DiningRoom(entrance);

        for(PawnColor c : PawnColor.values()) {
            assertEquals(0, diningRoom.count(c));
        }

    }

    /**
     * this method verifies that the filling of the diningRoom from the entrance works correctly
     */
    @Test
    public void fill(){

        PawnColor c = null;
        for (PawnColor color : PawnColor.values()){
            if (entrance.count(color)>0){
                c = color;
                break;
            }
        }

        int numStudents = entrance.count(c);

        diningRoom.fill(c);

        assertEquals(numStudents-1, entrance.count(c));
        assertEquals(1, diningRoom.count(c));


        }

    }


