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
    int green;
    int red;
    int blue;
    int yellow;
    int purple;

    @BeforeEach
    void setUp() {
        bag = new Bag();
        clouds = new ArrayList<>();
        cloud = new Cloud(bag);
        clouds.add(cloud);
        int arraySize = clouds.size();
        assertEquals(1, arraySize);
        cloud.fill(3);
        entrance = new Entrance(bag, clouds);
        assertEquals(7, entrance.count());
        green = entrance.count(PawnColor.GREEN);
        red = entrance.count(PawnColor.RED);
        blue = entrance.count(PawnColor.BLUE);
        yellow = entrance.count(PawnColor.YELLOW);
        purple = entrance.count(PawnColor.PURPLE);
        diningRoom = new DiningRoom(entrance);
    }

    @Test
    void fill() {

        assertEquals(7, yellow+green+purple+red+blue);

        diningRoom.fill(PawnColor.RED);
        diningRoom.fill(PawnColor.PURPLE);
        diningRoom.fill(PawnColor.BLUE);

        assertEquals(4, entrance.count());
        assertEquals(3, diningRoom.count());

            UUID cloudId = clouds.get(0).getId();
            UUID cloudId2 = cloud.getId();

            assertEquals(cloudId, cloudId2);
            int pawnCloud = cloud.count();
            int pawnCloud2 = clouds.get(0).count();

            assertEquals(3, pawnCloud);
            assertEquals(3, pawnCloud2);
            assertEquals(pawnCloud, pawnCloud2);

            entrance.fill(clouds.get(0).getId());


          assertEquals(7, entrance.count());
          assertEquals(0, cloud.count());

        }

    }



