package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.ArrayList;

public class GameModel {
    private ArrayList<Player> players;
    private int currentPlayer;
    private final IslandManager islandManager;
    private final ProfessorManager professorManager;
    private final Bag bag;

    public GameModel(){
        this.players=new ArrayList<>();
        this.currentPlayer=0;
        this.bag=new Bag();
        this.islandManager=new IslandManager(bag);
        this.professorManager=new ProfessorManager();
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
