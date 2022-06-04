package it.polimi.ingsw.model.studentmanagers;

import org.junit.Before;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

public class CloudTest extends StudentCounter {

    private Bag bag;
    private Cloud cloud;

    @Before
    public void setup(){
        bag = new Bag();
        cloud = new Cloud(bag);

    }

    /**
     * this method verifies that the filling of the clouds from the bag works correctly
     */
    @Test
    public void fill() {
        int pawnsInBagBefore = bag.count();
        int pawnsInCloudBefore = cloud.count();
        cloud.fill(3);
        assertEquals(pawnsInBagBefore - 3, bag.count());
        assertEquals(pawnsInCloudBefore + 3, cloud.count());
    }
}