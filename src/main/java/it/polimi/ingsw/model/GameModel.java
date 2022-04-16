package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.ArrayList;
import java.util.UUID;

public class GameModel {
    private ArrayList<Player> players;
    private IslandManager islandManager;
    private ProfessorManager professorManager;

    public GameModel(){

    }

    public void moveStudentToIsland(PawnColor color, UUID player, UUID island){
        //Player p = players. get(playerIdToIndex(player));
        //islandManager.moveStudentToIsland(color, island, p.getEntrance());
    }

    public void useMessengerEffect(){
        //TODO: implementation
    }

    /**
     * needed by Juggler class to be able to access a player's entrance
     * @param player UUID
     * @return reference to player
     */
    public Player getPlayerById(UUID player) {
        //TODO: implementation or maybe better to create a PlayerManager
        return null;
    }
}
