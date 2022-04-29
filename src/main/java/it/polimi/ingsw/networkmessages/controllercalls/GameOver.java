package it.polimi.ingsw.networkmessages.controllercalls;

import java.io.Serializable;
import java.util.UUID;

public class GameOver implements Serializable, RemoteMethodCall {
    private final UUID winner;

    public GameOver(UUID winner){
        this.winner = winner;
    }
    public UUID getWinner() {
        return winner;
    }
}
