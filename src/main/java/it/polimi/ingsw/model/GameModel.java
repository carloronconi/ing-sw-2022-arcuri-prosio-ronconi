package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.ArrayList;
import java.util.UUID;

public class GameModel {
    private ArrayList<Player> players;
    private int currentPlayer;
    private final IslandManager islandManager;
    private final ProfessorManager professorManager;
    private final Bag bag;
    private ArrayList<Cloud> clouds;
    private ArrayList<Integer> playedCards;
    private int bank;

    //creare altri costruttori in base alle differenti modalità del gioco oppure modificare quello esistente

    public GameModel(){
        this.players=new ArrayList<>();
        this.bag=new Bag();
        this.islandManager=new IslandManager(bag);
        this.professorManager=new ProfessorManager();
    }


    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /*
    public void moveStudentToIsland(PawnColor color, int islandIndex){
        islandManager.moveStudentToIsland(color,islandIndex, players.get(currentPlayer));
    }
     */


    /**
     * converts a player's id to its corresponding index within the players ArrayList
     * @param id is the id of the player whose index you want to know within the ArrayList
     * @return the player's index within the ArrayList or -1 if the player is not present
     */
    private int playerIdToIndex(UUID id){

        for(int i=0; i<players.size(); i++){
            if(id.equals(players.get(i).getId())){
                return i;
            }
        }
        return -1;
    }

    /**
     * converts the index of a player contained in the ArrayList to its corresponding ID
     * @param positionPlayer is the index of the player within the ArrayList whose id you want to know
     * @return the corresponding player id
     */
    private UUID playerIndexToId(int positionPlayer){

        return players.get(positionPlayer).getId();
    }

}
