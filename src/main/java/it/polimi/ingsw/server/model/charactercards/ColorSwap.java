package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.PawnColor;

import java.io.Serializable;

/**
 * inner class used to create a more convenient data structure for colorSwaps
 */
public class ColorSwap implements Serializable {
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