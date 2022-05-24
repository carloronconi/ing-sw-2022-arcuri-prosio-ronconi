package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.ClientHandler;
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
            //TODO: send it to everybody, maybe through gameModel (like gameState update that is
            //      delivered to everyone regardless of the fact that the thread is paused)
            virtualView.gameOver();
            virtualView.askPlayAgain();
        } else {
            virtualView.chooseCloud();
        }
    }
}
