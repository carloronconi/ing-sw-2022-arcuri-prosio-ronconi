package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;

public class Messenger extends Character {
    private final GameModel gameModel;

    /**
     * creates messenger with cost of 1
     * @param gameModel needed for the special effect
     */
    protected Messenger(GameModel gameModel) {
        super(1);
        this.gameModel = gameModel;
    }

    /**
     * calls method on gameModel to tell it that in the next call to moveMotherNature the player using it will have a bonus of 2 steps
     */
    public void useEffect(){
        gameModel.useMessengerEffect();
        if(!isCostIncreased()) increaseCost();
    }
}
