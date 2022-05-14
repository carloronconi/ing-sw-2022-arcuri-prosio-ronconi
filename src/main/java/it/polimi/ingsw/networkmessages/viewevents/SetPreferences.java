package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;

public class SetPreferences implements Serializable, SetupViewEvent {
    private final int numOfPlayers;
    private final GameMode gameMode;

    public SetPreferences(int numOfPlayers, GameMode gameMode) {
        this.numOfPlayers = numOfPlayers;
        this.gameMode = gameMode;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InterruptedException {

      while (!virtualView.isItMyTurn()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        virtualView.getAssistantCard();

    }
}
