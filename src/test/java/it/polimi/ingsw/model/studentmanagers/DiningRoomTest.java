package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;

public class DiningRoomTest extends StudentCounter{
    private Bag bag;
    private ArrayList<Cloud> clouds;
    private Entrance entrance;

    @BeforeEach
    void setup(){
        bag = new Bag();
        clouds = new ArrayList<>();

        IntStream.range(0, 3).forEach(i -> clouds.add(new Cloud(bag)));

        entrance = new Entrance(bag, clouds);

    }

    @Test
    void fill(){
        int pawnsInEntranceBefore = entrance.count(PawnColor.BLUE);
        int pawnsInDiningBefore = this.count(PawnColor.BLUE);
        movePawnFrom(entrance, PawnColor.BLUE);

        int pawnsInEntranceAfter = entrance.count(PawnColor.BLUE);
        int pawnsInDiningAfter = this.count(PawnColor.BLUE);

        assertEquals(pawnsInEntranceBefore-1, pawnsInEntranceAfter);
        assertEquals(pawnsInDiningBefore+1, pawnsInDiningAfter);

    }

}
