package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.UUID;

public class FlagBearer extends Character{
    private final IslandManager islandManager;

    public FlagBearer(IslandManager islandManager) {
        super(3);
        this.islandManager = islandManager;
    }

    public void useEffect(UUID island){
        islandManager.flagBearerEffect(island);
    }
}
