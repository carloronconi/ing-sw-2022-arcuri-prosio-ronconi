package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class ReadyToPlay implements Serializable, SetupViewEvent {
    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException, InterruptedException {
        for (int i = 1; i < 3; i++) {
            while (!virtualView.isItMyTurn()) {
                try {
                    wait(1000 * i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            virtualView.getAssistantCard();
        }
    }
}

