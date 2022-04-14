package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.UUID;

/**
 * subclass of StudentCounter that has attributes to reach the two neighbor tiles and attributes to
 * know if it is connected to those neighbor tiles or not
 */
public class IslandTile extends StudentCounter{
    private Player owner;
    private int size;
    private final UUID id;

    /**
     * initializes with 0 students
     */
    public IslandTile() {
        super();
        this.owner=null;
        size = 1;
        this.id=UUID.randomUUID();
    }

    /**
     * initializes with 1 random students from the islandManager
     * @param manager IslandManager that is initializing the tile
     */
    public IslandTile(IslandManager manager, int numOfStudents){
        super();
        this.owner=null;
        size = 1;
        this.id=UUID.randomUUID();
        for (int i = 0; i < numOfStudents; i++) {
            movePawnFrom(manager);
        }
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public UUID getId(){ return id; }



    public void moveAllPawnsFrom(IslandTile otherIsland){
        size++;
        for (PawnColor c: PawnColor.values()) {
            for (int i = 0; i < otherIsland.count(c); i++) {
                movePawnFrom(otherIsland,c);
            }
        }
    }


    /**
     * is called by the island on which mother nature arrives and makes a check among the various players
     * in order to find the one that has the greatest influence on the island itself
     * @param professorManager takes in a ProfessorManager object that contains the professors and
     *                         their players who own them
     * @return the player who has the most influence on that island
     */
    public Player checkNewOwner(ProfessorManager professorManager){

        HashMap<Player, Integer> supportNewOwner = new HashMap<>();
        for(Player player : professorManager.playersContained()){
            int numStudents=0;
            for(PawnColor pawnColor : professorManager.colorsAssociateToPlayer(player)){
                numStudents += this.count(pawnColor);
            }

            if(player.equals(owner)){ //in questo caso devo mettere this.owner o solo owner?
                numStudents++;
            }

            supportNewOwner.put(player, numStudents);
        }


        Player supportPlayer = new Player();
        int numInfluenceStudents=-1;
        for(Player player : supportNewOwner.keySet()){
            if(supportNewOwner.get(player)>numInfluenceStudents){
                numInfluenceStudents=supportNewOwner.get(player);
                supportPlayer=player;
            }else if(supportNewOwner.get(player)==numInfluenceStudents){
                supportPlayer=null;
            }
        }

        return supportPlayer;
    }


}
