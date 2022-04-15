package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;

public class Messenger extends Character {
    private final GameModel gameModel;

    protected Messenger(GameModel gameModel) {
        super(1);
        this.gameModel = gameModel;
    }

    public void useEffect(){
        gameModel.useMessengerEffect();
        if(!isCostIncreased()) assertCostIncreased();
    }
}
