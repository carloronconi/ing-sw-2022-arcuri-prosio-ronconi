package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.studentmanagers.IslandManager;

public class Centaur extends Character{
    private final IslandManager islandManager;

    public Centaur(IslandManager islandManager) {
        super(AvailableCharacter.CENTAUR.getInitialCost());
        this.islandManager = islandManager;
    }

    public void useEffect(){
        islandManager.assertCentaurEffect();
        if (!isCostIncreased()) increaseCost();
    }
}
