package it.polimi.ingsw.networkmessages;

import java.io.Serializable;

public class SetPlayAgain implements Serializable {
        private final boolean playAgain;

        public SetPlayAgain(boolean playAgain){
            this.playAgain = playAgain;
        }

    public boolean isPlayAgain() {
        return playAgain;
    }

}
