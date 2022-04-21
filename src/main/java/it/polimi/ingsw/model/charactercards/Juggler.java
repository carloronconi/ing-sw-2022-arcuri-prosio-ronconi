package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.CharacterStudentCounter;
import it.polimi.ingsw.model.studentmanagers.Entrance;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.IntStream;

public class Juggler extends SwapperCharacter {
    private final CharacterStudentCounter studentCounter;

    /**
     * initialises parameters needed for effect, draws 6 students from the bag and initialises super with 3 maximum color swaps
     * @param gameModel needed for special effect
     * @param bag needed to draw students
     */
    public Juggler(GameModel gameModel, Bag bag) {
        super(1, gameModel, 3);
        studentCounter = new CharacterStudentCounter();
        IntStream.range(0,6).forEach(i -> studentCounter.takeStudentFrom(bag));
    }

    /**
     * to be called after calling setupColorSwaps at least once and at most three times in order to make the
     * swap with the Juggler
     * @param player the player asking for the swap to happen
     * @throws IllegalStateException when never called setupColorSwap
     */
    public void useEffect(UUID player) throws IllegalStateException, NoSuchFieldException {
        if(colorSwaps.isEmpty()) throw new IllegalStateException("never called setupColorSwaps before using the effect: has to be called at least once");
        Entrance entrance = gameModel.getPlayerById(player).getEntrance();
        for(ColorSwap cs : colorSwaps) entrance.swapStudent(this.studentCounter, cs.getGive(), cs.getTake());
        if (!isCostIncreased()) increaseCost();
    }
}
