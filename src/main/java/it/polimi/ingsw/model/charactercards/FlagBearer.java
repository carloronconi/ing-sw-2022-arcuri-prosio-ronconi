package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.UUID;

public class FlagBearer extends Character{
    private final IslandManager islandManager;

    /**
     * creates flagBearer with cost of 3
     * @param islandManager needed for special effect
     */
    protected FlagBearer(IslandManager islandManager) {
        super(3);
        this.islandManager = islandManager;
    }

    /**
     * calls method on islandManager to tell it to perform checkNewOwner and mergeIslands on an island even if mother nature is not there
     * @param island where to perform checkNewOwner and mergeIslands
     */
    public void useEffect(UUID island){
        islandManager.flagBearerEffect(island);
        if (!isCostIncreased()) increaseCost();
    }
}
