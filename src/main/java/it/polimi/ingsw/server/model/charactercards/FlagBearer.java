package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.charactercards.effectarguments.EffectWithIsland;
import it.polimi.ingsw.server.model.studentmanagers.IslandManager;

import java.util.UUID;

public class FlagBearer extends Character implements EffectWithIsland {
    private final IslandManager islandManager;
    private UUID island;

    /**
     * creates flagBearer with cost of 3
     * @param islandManager needed for special effect
     */
    protected FlagBearer(IslandManager islandManager) {
        super(AvailableCharacter.FLAGBEARER.getInitialCost());
        this.islandManager = islandManager;
    }

    /**
     * calls method on islandManager to tell it to perform checkNewOwner and mergeIslands on an island even if mother nature is not there
     */
    public void useEffect() throws NoSuchFieldException {
        if (island==null) throw new IllegalStateException();
        islandManager.useFlagBearerEffect(island);
        if (!isCostIncreased()) increaseCost();
        island = null;
    }

    @Override
    public void setEffectIsland(UUID island) {
        this.island = island;
    }
}
