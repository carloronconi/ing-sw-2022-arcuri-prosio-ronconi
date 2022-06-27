package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.Juggler;
import it.polimi.ingsw.server.model.charactercards.Monk;
import it.polimi.ingsw.server.model.charactercards.Musician;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvailableCharacterTest {

    @Test
    void getCharacterClass() {
        assertEquals(Monk.class, AvailableCharacter.MONK.getCharacterClass());
    }

    @Test
    void getCost() {
        assertEquals(AvailableCharacter.MONK.getInitialCost(), 1);
    }

    @Test
    void getMaxColorSwaps() {
        assertEquals(AvailableCharacter.MUSICIAN.getMaxColorSwaps(), Musician.getMaxColorSwaps());
    }

    @Test
    void getStudents() {
        assertEquals(AvailableCharacter.JUGGLER.getStudents(), Juggler.getStudents());
    }
}