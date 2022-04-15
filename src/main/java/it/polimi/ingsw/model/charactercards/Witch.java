package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.UUID;
/**
 * one of the concrete subclasses of character representing a specific character card
 */
public class Witch extends Character{
    private int availableBans;
    private final IslandManager islandManager;

    /**
     * creates a witch with cost of 2 and 4 available bans
     * @param islandManager to be used in useEffect
     */
    protected Witch(IslandManager islandManager) {
        super(2);
        availableBans = 4;
        this.islandManager = islandManager;
    }

    /**
     * checks if there are any more available bans, calls method on islandManager,
     * then decreases the available bans and increases the cost of the character
     * @param island to be banned from evaluation in the next turn
     * @throws IllegalStateException if no more available bans
     */
    public void useEffect(UUID island) throws IllegalStateException {
        if(availableBans < 1) throw new IllegalStateException();
        islandManager.useWitchEffect(island, this);
        availableBans--;
        if(!isCostIncreased()) assertCostIncreased();
    }

    /**
     * to be called only by the islandManager when motherNature ends on an island and the evaluation is avoided,
     * so the number of available bans needs to be restored
     * @param islandManager to ensure the method is only called by the islandManager (will pass this as a parameter)
     * @throws IllegalArgumentException if other islandManager is passed
     */
    public void increaseAvailableBans(IslandManager islandManager) throws IllegalArgumentException {
        if (this.islandManager != islandManager) throw new IllegalArgumentException();
        availableBans++;
    }
}
