package it.polimi.ingsw.networkmessages.viewevents;

import java.io.Serializable;

public class SetPlayAgain implements Serializable, SetupViewEvent {
        private final boolean playAgain;

        public SetPlayAgain(boolean playAgain){
            this.playAgain = playAgain;
        }

    public boolean isPlayAgain() {
        return playAgain;
    }

}
