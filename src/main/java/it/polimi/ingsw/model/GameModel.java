package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.ArrayList;
import java.util.UUID;

public class GameModel {
    private ArrayList<Player> players;
    private int currentPlayer;
    private IslandManager islandManager;
    private ProfessorManager professorManager;

    public GameModel(){

    }

    public void moveStudentToIsland(PawnColor color, UUID player, UUID island){
        //Player p = players. get(playerIdToIndex(player));
        //islandManager.moveStudentToIsland(color, island, p.getEntrance());
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
