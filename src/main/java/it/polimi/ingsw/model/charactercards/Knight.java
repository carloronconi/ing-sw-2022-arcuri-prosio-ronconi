package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.studentmanagers.IslandManager;

public class Knight extends Character {
    private final IslandManager islandManager;

    public Knight(IslandManager islandManager) {
        super(2);
        this.islandManager = islandManager;
    }

    public void useEffect(){
        islandManager.assertKnightEffect();
    }
}
