package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;

import java.util.ArrayList;
import java.util.UUID;

/**
 * abstract class used to avoid code duplication for the two similar classes Juggler and Musician
 */
public abstract class SwapperCharacter extends Character {
    protected final GameModel gameModel;
    protected final ArrayList<ColorSwap> colorSwaps;

    /**
     * inner class used to create a more convenient data structure for colorSwaps
     */
    protected class ColorSwap {
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

    public SwapperCharacter(int cost, GameModel gameModel) {
        super(cost);
        this.gameModel = gameModel;
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

    public abstract void useEffect(UUID player) throws NoSuchFieldException;

}
