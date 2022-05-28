package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.Identifiable;
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
public class IslandTile extends StudentCounter implements Identifiable {
    private Player owner;
    private int size;
    private final UUID id;
    public boolean ban;

    /**
     * initializes with 0 students
     */
    public IslandTile() {
        super();
        ban = false;
        size = 1;
        this.id=UUID.randomUUID();
    }

    /**
     * initializes with 1 random students from the islandManager
     * @param manager IslandManager that is initializing the tile
     */
    public IslandTile(IslandManager manager, int numOfStudents){
        super();
        size = 1;
        this.id=UUID.randomUUID();
        for (int i = 0; i < numOfStudents; i++) {
            movePawnFrom(manager);
        }
    }

    public int getSize() {
        return size;
    }

    public Player getOwner() {
        return owner;
    }

    public UUID getId(){ return id; }


    /**
     * it is called when unification between two islands is required and transfers all students
     * from the past island as input to the island calling this method
     * @param otherIsland is the island from which students are transferred
     */
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
    public Player checkNewOwner(ProfessorManager professorManager, boolean centaurEffect, Player knightEffectOwner, PawnColor mushroomMerchantEffect){
        /*
          this method is called by an island and is used to control its new owner.
          A Player-integer table is created. You iterate over all the players by setting a number of students
          equal to 0 for each one and, for each player, you iterate over the colors of the professor.
          The number of students of each color is tracked and, at the end of the iteration,
          the total number of students the player has influence on the island under consideration is obtained.
          If the player was already the owner of this island, then the influence increases by one.
          The Player-Integer pair is inserted into another supporting table.
         */
        HashMap<Player, Integer> playerPointsMap = new HashMap<>();

        ArrayList<PawnColor> colorsOnIsland = new ArrayList<>();

        for (PawnColor color: PawnColor.values()){
            if (this.count(color)>0) colorsOnIsland.add(color);
        }

        for(PawnColor color : colorsOnIsland){
            Player colorOwner = professorManager.getProfessorOwner(color);
            if(colorOwner == null) continue;
            playerPointsMap.put(colorOwner, this.count(color));
        }

        if(owner!=null){
            if (playerPointsMap.get(owner) == null) playerPointsMap.put(owner, size);
            else playerPointsMap.put(owner, playerPointsMap.get(owner)+size);
        }

        if (playerPointsMap.isEmpty()) return null;

        Player bestPlayer = null;
        int bestScore = -1;
        for (Player currPlayer : playerPointsMap.keySet()){
            int currScore = playerPointsMap.get(currPlayer);
            if (currScore>bestScore){
                bestScore = currScore;
                bestPlayer = currPlayer;
            } else if (currScore == bestScore) {
                if (currPlayer == owner || bestPlayer == owner) bestPlayer = owner;
                else bestPlayer = null;
            }
        }

        owner = bestPlayer;
        return owner;

/*
        for(Player player : professorManager.playersContained()){
            int numStudents=0;
            ArrayList<PawnColor> colors = professorManager.colorsAssociateToPlayer(player);
            colors.remove(mushroomMerchantEffect); // in this turn the color is not considered in the evaluation

            for(PawnColor pawnColor : colors){
                numStudents += this.count(pawnColor);
            }

            if(player==owner && !centaurEffect){
                numStudents+= size;
            }

            if(player==knightEffectOwner) numStudents+=2;

            playerPointsMap.put(player, numStudents);
        }

        /*
          in this case a negative value of affected students is set. It iterates over all the players contained
          in the support table and the previously set value allows you to define the first player as the player
          who has the influence on the island with the relative number of students affected.
          This facilitates comparison later. The number of students of the next player will be compared
          with that of the previous player and, if it is greater, this player will own (at least for now) the island,
          otherwise the previous player will still own it. If, on the other hand, this player should have
          a number of students equal to the previous one, then the support player will be set as null
          because neither of them will own the island. However, the number of students will be saved
          because if the third player has more students than the other previous players,
          then this will be set as the player who will own the island.
         *//*
        Player supportPlayer = null;
        int numInfluenceStudents=-1;
        for(Player player : playerPointsMap.keySet()){
            if(playerPointsMap.get(player)>numInfluenceStudents){
                numInfluenceStudents=playerPointsMap.get(player);
                supportPlayer=player;

            }else if(playerPointsMap.get(player)==numInfluenceStudents){
                supportPlayer=null;
            }
        }

        if (supportPlayer!= owner && supportPlayer!= null) owner=supportPlayer;

        return owner;*/
    }


}
