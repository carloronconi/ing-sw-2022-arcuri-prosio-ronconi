package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class SetPlayAgain implements Serializable, SetupViewEvent {
        private final boolean playAgain;

        public SetPlayAgain(boolean playAgain){
            this.playAgain = playAgain;
        }

    public boolean isPlayAgain() {
        return playAgain;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {

    }
}
