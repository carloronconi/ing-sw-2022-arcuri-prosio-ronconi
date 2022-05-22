package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class ReadyToPlay implements Serializable, SetupViewEvent {
    private static boolean preferencesWereSet = false;

    public ReadyToPlay(){

    }

    @Override
   public void processMessage(VirtualView virtualView) throws InvalidObjectException, InterruptedException {
      /*  while (!virtualView.isItMyTurn()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        virtualView.getAssistantCard();  */
    }
}


