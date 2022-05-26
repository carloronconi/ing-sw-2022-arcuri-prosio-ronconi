package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;

/**
 * inner class used to create a more convenient data structure for colorSwaps
 */
public class ColorSwap {
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
