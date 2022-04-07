package it.polimi.ingsw.model.studentmanagers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest extends StudentCounter {


    @Test
    void fill() {
        Bag bag = new Bag();
        Cloud cloud = new Cloud(bag);
        int pawnsInBagBefore = bag.count();
        int pawnsInCloudBefore = cloud.count();
        cloud.fill(3);
        assertEquals(pawnsInBagBefore - 3, bag.count());
        assertEquals(pawnsInCloudBefore + 3, cloud.count());
    }
}