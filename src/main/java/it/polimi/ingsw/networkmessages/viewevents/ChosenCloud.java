package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.controller.TurnState;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.UUID;

public class ChosenCloud implements Serializable, GameViewEvent {
    private final UUID cloud;

    public ChosenCloud(UUID cloud){
        this.cloud = cloud;
    }
    public UUID getCloud() {
        return cloud;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {
        virtualView.notifyController(this);

        synchronized (SetAssistantCard.class){
            //turn ends after setting the assistant card
            virtualView.playerFinishedTurn();
            SetAssistantCard.class.notifyAll();
        }

        synchronized (ChosenCloud.class){
            ChosenCloud.class.notifyAll();
        }
        if(!(virtualView.isItMyTurn() && virtualView.getTurnControllerState() == TurnState.PLANNING)){
            synchronized (ChosenCloud.class){
                while (!virtualView.isItMyTurn()) {
                    try {
                        ChosenCloud.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("thread " + virtualView.getThisInstanceNumber() + " starts its turn in planning phase");
        virtualView.getAssistantCard();
    }
}
