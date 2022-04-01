package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentCounterTest {

    private StudentCounter scFull;
    private StudentCounter scEmpty;

    @BeforeEach
    void setUp() {
        scEmpty = new StudentCounter();
        scFull = new StudentCounter(true);
        for(PawnColor c : PawnColor.values()){
            assertEquals(26, scFull.count(c), "Each color should have 26 pawns");
            assertEquals(0,scEmpty.count(c), "Each color should have 0 pawns");
        }
    }

    @Test
    void movePawnFrom_SelectedColor() {
        for (PawnColor c : PawnColor.values()) {
            int numBeforeFull = scFull.count(c);
            int numBeforeEmpty = scEmpty.count(c);
            try{
                scEmpty.movePawnFrom(scFull, c);
            } catch (IllegalArgumentException e){
                fail();
            }
            assertEquals(numBeforeFull - 1, scFull.count(c), "Should have reduced the number of pawns of a color by 1");
            assertEquals(numBeforeEmpty + 1, scEmpty.count(c), "Should have increased the number of pawns of a color by 1");
        }
        assertEquals(130, scFull.count() + scEmpty.count(), "Total number of pawns shouldn't have changed");
    }

    @Test
    void movePawnFrom_RandomColor() {
        int totalBeforeFull = scFull.count();
        int totalBeforeEmpty = scEmpty.count();
        StudentCounter scFullBefore = new StudentCounter(true);
        StudentCounter scEmptyBefore = new StudentCounter();
        try{
            PawnColor c = scEmpty.movePawnFrom(scFull);
            assertEquals(scFullBefore.count(c) - 1, scFull.count(c), "Should have increased number of pawns of random color by one");
            assertEquals(scEmptyBefore.count(c) + 1, scEmpty.count(c), "Should have decreased number of pawns of random color by one");
        } catch (IllegalArgumentException e){
            fail();
        }
        assertEquals(totalBeforeFull - 1, scFull.count(), "Total number of pawns should have decreased by one");
        assertEquals(totalBeforeEmpty + 1, scEmpty.count(), "Total number of pawns should have increased by one");

    }

    // TODO: useless because already tested w/ the previous tests?
    @Test
    void count() {
    }
}