package it.polimi.ingsw.networkmessages;

import java.io.Serializable;
import java.util.UUID;

public class GameOver implements Serializable {
    private final UUID winner;

    public GameOver(UUID winner){
        this.winner = winner;
    }
    public UUID getWinner() {
        return winner;
    }
}
