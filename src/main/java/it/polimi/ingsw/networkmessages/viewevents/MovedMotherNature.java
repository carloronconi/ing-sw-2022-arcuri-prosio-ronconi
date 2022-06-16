package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class MovedMotherNature implements Serializable, GameViewEvent {
    private final int motherNatureSteps;

    public MovedMotherNature(int motherNatureSteps){
        this.motherNatureSteps = motherNatureSteps;
    }

    public int getMotherNatureSteps(){
        return motherNatureSteps;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {
        if(virtualView.isMNMoveIllegal(motherNatureSteps)){
            virtualView.invalidMNMove();
            return;
        }

        virtualView.notifyController(this);
        if (virtualView.isGameOver()){
            virtualView.gameOver(virtualView.getGameWinner());
        } else {
            virtualView.chooseCloud();
        }
    }
}
