package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * abstract class used to avoid code duplication for the two similar classes Juggler and Musician
 */
public abstract class SwapperCharacter extends Character {
    protected final List<Player> players;
    protected final ArrayList<ColorSwap> colorSwaps;
    private final int maxColorSwaps;

    public SwapperCharacter(int cost, List<Player> players, int maxColorSwaps) {
        super(cost);
        this.players = players;
        this.maxColorSwaps = maxColorSwaps;
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
        if (colorSwaps.size()>maxColorSwaps-1) throw new IllegalStateException("already set up maximum number of students to be swapped");
        colorSwaps.add(new ColorSwap(give, take));
    }
}
