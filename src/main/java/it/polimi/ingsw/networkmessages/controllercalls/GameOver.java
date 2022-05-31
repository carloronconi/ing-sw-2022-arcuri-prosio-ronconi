package it.polimi.ingsw.networkmessages.controllercalls;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

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

    @Override
    public void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager) {
        viewInterface.gameOver(winner);
    }
}
