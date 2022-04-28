package it.polimi.ingsw.networkmessages;

import it.polimi.ingsw.controller.GameMode;

import java.io.Serializable;

public class SetPreferences implements Serializable {
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
}
