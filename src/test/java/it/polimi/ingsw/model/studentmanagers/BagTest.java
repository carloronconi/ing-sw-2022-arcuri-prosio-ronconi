package it.polimi.ingsw.model.studentmanagers;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class BagTest extends StudentCounter {
    private Bag bag;

    @BeforeEach
    void setUp() {
        bag = new Bag();
    }
}