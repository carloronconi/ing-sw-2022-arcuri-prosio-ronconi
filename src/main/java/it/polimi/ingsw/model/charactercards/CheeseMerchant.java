package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;

public class CheeseMerchant extends Character {
    private final GameModel gameModel;

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
    public void useEffect(){
        gameModel.assertCheeseMerchantEffect();
        if (!isCostIncreased()) increaseCost();
    }
}
