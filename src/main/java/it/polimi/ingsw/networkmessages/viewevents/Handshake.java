package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class Handshake implements Serializable, SetupViewEvent {

    @Override
    public void processMessage(VirtualView virtualView) {
        virtualView.getClientHandler().sendAcknowledgement();
       /* if (virtualView.getThisInstanceNumber() == 0) {
            virtualView.getPreferences();
        }else{
            virtualView.sendAcknowledgement();
        }*/
        virtualView.getNickname();
    }
}
