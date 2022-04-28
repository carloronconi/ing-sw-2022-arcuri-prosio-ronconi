package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

public class MushroomMerchant extends Character {
    private final IslandManager islandManager;

    public MushroomMerchant(IslandManager islandManager) {
        super(3);
        this.islandManager = islandManager;
    }

    public void useEffect(PawnColor color){
        islandManager.setMushroomMerchantEffect(color);
        if (!isCostIncreased()) increaseCost();
    }
}
