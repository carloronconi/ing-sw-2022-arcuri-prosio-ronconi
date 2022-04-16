package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.CharacterStudentCounter;
import it.polimi.ingsw.model.studentmanagers.Entrance;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.IntStream;

public class Juggler extends Character {
    private final GameModel gameModel;
    private final CharacterStudentCounter studentCounter;
    private final ArrayList<ColorSwap> colorSwaps;

    /**
     * inner class used to create a more convenient data structure for colorSwaps
     */
    private class ColorSwap {
        private final PawnColor give;
        private final PawnColor take;

        public ColorSwap(PawnColor give, PawnColor take) {
            this.give = give;
            this.take = take;
        }

        public PawnColor getGive() {
            return give;
        }

        public PawnColor getTake() {
            return take;
        }
    }

    /**
     * initialises parameters needed for effect and draws 6 students from the bag
     * @param gameModel needed for special effect
     * @param bag needed to draw students
     */
    public Juggler(GameModel gameModel, Bag bag) {
        super(1);
        this.gameModel = gameModel;
        studentCounter = new CharacterStudentCounter();
        IntStream.range(0,6).forEach(i -> studentCounter.takeStudentFrom(bag));
        colorSwaps = new ArrayList<>();
    }

    /**
     * to be called at least once and at most three times before calling useEffect in order tell the Juggler
     * which color couples we want to exchange
     * @param give student pawn color that we want to give in the swap
     * @param take student pawn color that we want to receive in the swap
     * @throws IllegalStateException if exceeded maximum number of swaps (three)
     */
    public void setupColorSwaps(PawnColor give, PawnColor take) throws IllegalStateException {
        if (colorSwaps.size()>2) throw new IllegalStateException("already set up maximum number of students to be swapped");
        colorSwaps.add(new ColorSwap(give, take));
    }

    /**
     * to be called after calling setupColorSwaps at least once and at most three times in order to make the
     * swap with the Juggler
     * @param player the player asking for the swap to happen
     * @throws IllegalStateException when never called setupColorSwap
     */
    public void useEffect(UUID player) throws IllegalStateException {
        if(colorSwaps.isEmpty()) throw new IllegalStateException("never called setupColorSwaps before using the effect: has to be called at least once");
        Entrance entrance = gameModel.getPlayerById(player).getEntrance();
        for(ColorSwap cs : colorSwaps) entrance.swapStudent(this.studentCounter, cs.getGive(), cs.getTake());
    }
}
