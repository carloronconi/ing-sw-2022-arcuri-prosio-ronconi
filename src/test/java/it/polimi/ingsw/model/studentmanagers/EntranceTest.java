package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class EntranceTest extends StudentCounter {
    private ArrayList<Cloud> clouds;
    private Bag bag;
    private  Entrance entrance;
    private DiningRoom diningRoom;

    @BeforeEach
    void setUp() {
        bag = new Bag();
        clouds = new ArrayList<>();
        IntStream.range(0,3).forEach(i -> clouds.add(new Cloud(bag)));

        entrance = new Entrance(bag,clouds);
        assertEquals(7, entrance.count());
        diningRoom = new DiningRoom(entrance);
    }

    @Test
    void fill() {
        int removed = 0;
        for (PawnColor c: PawnColor.values()) {
            if (entrance.count(c) > 0){
                diningRoom.fill(c);
                removed++;
            }
            if (removed == 3) break;
        }
        assertEquals(4, entrance.count());
        clouds.get(1).fill(3);
        assertEquals(3, clouds.get(1).count());
        entrance.fill(clouds.get(0).getId());
        assertEquals(0, clouds.get(1).count());
        assertEquals(7,entrance.count());
    }
}