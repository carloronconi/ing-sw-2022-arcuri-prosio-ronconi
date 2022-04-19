package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.studentmanagers.IslandManager;

public class Centaur extends Character{
    private final IslandManager islandManager;

    public Centaur(IslandManager islandManager) {
        super(3);
        this.islandManager = islandManager;
    }

    public void useEffect(){
        islandManager.assertCentaurEffect();
        if (!isCostIncreased()) increaseCost();
    }
}
