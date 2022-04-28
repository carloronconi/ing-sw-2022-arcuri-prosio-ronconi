package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.Identifiable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest extends StudentCounter {

    private Bag bag;
    private Cloud cloud;

    @BeforeEach
    void setup(){
        bag = new Bag();
        cloud = new Cloud(bag);

    }

    @Test
    void fill() {
        int pawnsInBagBefore = bag.count();
        int pawnsInCloudBefore = cloud.count();
        cloud.fill(3);
        assertEquals(pawnsInBagBefore - 3, bag.count());
        assertEquals(pawnsInCloudBefore + 3, cloud.count());
    }
}