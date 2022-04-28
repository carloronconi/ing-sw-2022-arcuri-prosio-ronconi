package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class DiningRoomTest extends StudentCounter{
    private Bag bag;
    private ArrayList<Cloud> clouds;
    private Entrance entrance;
    private DiningRoom diningRoom;

    @BeforeEach
    void setup(){
        bag = new Bag();
        clouds = new ArrayList<>();
        IntStream.range(0, 3).forEach(i -> clouds.add(new Cloud(bag)));
        entrance = new Entrance(bag, clouds);
        diningRoom = new DiningRoom(entrance);

        for(PawnColor c : PawnColor.values()) {
            assertEquals(0, diningRoom.count(c));
        }

    }

    @Test
    void fill(){

            int pawnsInEntranceBeforeBLUE = entrance.count(PawnColor.BLUE);
            int pawnsInDiningBeforeBLUE = diningRoom.count(PawnColor.BLUE);

        try {
                diningRoom.fill(PawnColor.BLUE);

            }catch(IllegalArgumentException e){
                fail();
            }
            int pawnsInEntranceAfterBLUE = entrance.count(PawnColor.BLUE);
            int pawnsInDiningAfterBLUE = diningRoom.count(PawnColor.BLUE);


        assertEquals(pawnsInEntranceBeforeBLUE - 1, pawnsInEntranceAfterBLUE);
            assertEquals(pawnsInDiningBeforeBLUE + 1, pawnsInDiningAfterBLUE);

        }

    }


