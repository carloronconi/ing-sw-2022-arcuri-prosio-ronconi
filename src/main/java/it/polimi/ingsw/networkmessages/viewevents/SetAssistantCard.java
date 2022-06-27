package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.controller.GameMode;
import it.polimi.ingsw.server.controller.TurnState;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class SetAssistantCard implements Serializable, GameViewEvent {
    private final Integer numCard;

    public SetAssistantCard(Integer numCard){
        this.numCard=numCard;
    }

    public Integer getCard(){
        return numCard;
    }


    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {
        if (virtualView.isAssistantCardIllegal(getCard())) {
            virtualView.invalidAssistantCard();
            return;
        }


        virtualView.notifyController(this);

        synchronized (ReadyToPlay.class){
            //turn ends after setting the assistant card
            virtualView.playerFinishedTurn();
            ReadyToPlay.class.notifyAll();
        }

        synchronized (ChosenCloud.class){
            ChosenCloud.class.notifyAll();
        }

        System.out.println("thread " + virtualView.getThisInstanceNumber() + " finished its turn in planning phase");

        System.out.println("thread " + virtualView.getThisInstanceNumber() + " woke up to start action phase");

        synchronized (SetAssistantCard.class){
            SetAssistantCard.class.notifyAll();
        }
        if(!(virtualView.isItMyTurn() && virtualView.getTurnControllerState() == TurnState.ACTION)){
            synchronized (SetAssistantCard.class){
                while (!virtualView.isItMyTurn()) {
                    try {
                        SetAssistantCard.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("thread " + virtualView.getThisInstanceNumber() + " starts its turn in action phase");
        if (virtualView.getGameMode() == GameMode.HARD){
            virtualView.chooseCharacter();
        } else {
            virtualView.moveStudent();
        }

    }


}

