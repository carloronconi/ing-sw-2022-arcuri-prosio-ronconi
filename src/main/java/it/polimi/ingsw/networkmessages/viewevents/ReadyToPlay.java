package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class ReadyToPlay implements Serializable, SetupViewEvent {
    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException, InterruptedException {

            for(int i=0; i<2; i++){
                if(!virtualView.isItMyTurn()){
                    virtualView.threadWait();
                }
                virtualView.getAssistantCard();

            }

    }
}

