package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.Identifiable;
import it.polimi.ingsw.model.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class EntranceTest extends StudentCounter {
    private ArrayList<Cloud> clouds;
    private Bag bag;
    private Entrance entrance;
    private DiningRoom diningRoom;
    private Cloud cloud;

    @BeforeEach
    void setUp() {
        bag = new Bag();
        clouds = new ArrayList<>();
        cloud = new Cloud(bag);
        clouds.add(cloud);
        cloud.fill(3);
        entrance = new Entrance(bag, clouds);
        assertEquals(7, entrance.count());
        diningRoom = new DiningRoom(entrance);
    }

    @Test
    void fill() {

        try {
            diningRoom.fill(PawnColor.BLUE);

        } catch (IllegalArgumentException e) {
            fail();
        }


        assertEquals(6, entrance.count());
        assertEquals(1, diningRoom.count());

        entrance.movePawnFrom(clouds.get(0));

        assertEquals(7, entrance.count());
        assertEquals(2, clouds.get(0).count());

    }

}


