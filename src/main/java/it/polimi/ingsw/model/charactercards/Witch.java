package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.UUID;

public class Witch extends Character{
    private int availableBans;
    private final IslandManager islandManager;

    protected Witch(IslandManager islandManager) {
        super(2);
        availableBans = 4;
        this.islandManager = islandManager;
    }

    public void useEffect(UUID island) throws IllegalStateException {
        if(availableBans < 1) throw new IllegalStateException();
        islandManager.useWitchEffect(island, this);
        availableBans--;
        if(!isCostIncreased()) assertCostIncreased();
    }

    public void increaseAvailableBans(IslandManager islandManager) throws IllegalArgumentException {
        if (this.islandManager != islandManager) throw new IllegalArgumentException();
        availableBans++;
    }
}
