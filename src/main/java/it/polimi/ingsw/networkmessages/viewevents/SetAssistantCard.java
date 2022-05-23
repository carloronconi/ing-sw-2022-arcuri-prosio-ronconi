package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.server.ClientHandler;
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
            //temporarily have turn finish after just setting the assistant card
            virtualView.playerFinishedTurn();
            ReadyToPlay.class.notifyAll();
        }
    }


}

