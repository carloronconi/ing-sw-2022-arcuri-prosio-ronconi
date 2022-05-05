package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithPlayer;

import java.util.UUID;

public class CheeseMerchant extends Character implements EffectWithPlayer {
    private final GameModel gameModel;
    private UUID player;

    /**
     * creates cheeseMerchant with cost of 2
     * @param gameModel needed for the special effect
     */
    protected CheeseMerchant(GameModel gameModel) {
        super(2);
        this.gameModel = gameModel;
    }

    /**
     * calls method on professorManager to make sure it performs the comparison of number of students in the special way
     */
    public void useEffect() throws NoSuchFieldException {
        if (player == null) throw new IllegalStateException();
        gameModel.assertCheeseMerchantEffect(player);
        if (!isCostIncreased()) increaseCost();
        player = null;
    }

    @Override
    public void setEffectPlayer(UUID player) {
        this.player = player;
    }
}
