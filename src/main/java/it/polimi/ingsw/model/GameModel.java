package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.ArrayList;

public class GameModel {
    private ArrayList<Player> players;
    private int currentPlayer;
    private IslandManager islandManager;
    private ProfessorManager professorManager;

    public GameModel(){

    }

    public void moveStudentToIsland(PawnColor color, int islandIndex){
        islandManager.moveStudentToIsland(color,islandIndex, players.get(currentPlayer));
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
