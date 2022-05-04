package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithColor;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.UUID;

public class MushroomMerchant extends Character implements EffectWithColor {
    private final IslandManager islandManager;
    private PawnColor color;

    public MushroomMerchant(IslandManager islandManager) {
        super(3);
        this.islandManager = islandManager;
    }


    public void useEffect(){
        if (color==null) throw new IllegalStateException();
        islandManager.setMushroomMerchantEffect(color);
        if (!isCostIncreased()) increaseCost();
        color = null;
    }

    @Override
    public void setEffectColor(PawnColor color) {
        this.color = color;
    }
}
