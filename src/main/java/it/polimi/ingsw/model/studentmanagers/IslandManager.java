package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ProfessorManager;

import java.util.ArrayList;

/**
 * Class used to manage islands and the merging of islands in archipelagos
 */
public class IslandManager extends StudentCounter {
    private final ArrayList<IslandTile> islands;
    private int motherNaturePosition;

    /**
     * Constructor draws 2 tiles for each color from the bag, then adds 12 islands and puts in the first 10
     * islands 2 random student pawns, then creates one archipelago for each island and adds it to archipelagos
     * @param bag needed to draw the 10 students to initialize the islands
     */
    public IslandManager(Bag bag) {
        super();
        motherNaturePosition = 0;

        for (PawnColor c: PawnColor.values()) {
            movePawnFrom(bag, c);
            movePawnFrom(bag, c);
        }

        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            IslandTile it;
            if(i % 6 == 0){
                it = new IslandTile();
            } else {
                it = new IslandTile(this, 2);
            }
            islands.add(it);
        }
    }


    public void moveStudentToIsland(PawnColor color, int islandIndex, Player player){
        IslandTile island = islands.get(islandIndex);
        island.movePawnFrom(player.getEntrance(), color);
    }

    //TODO: separate method to change owner? or merge automatically when two neighbor islands have same owner?
    private void mergeIslands(int first, int second) throws IllegalArgumentException{
        IslandTile firstIsland = islands.get(first);
        IslandTile secondIsland = islands.get(second);
        if (firstIsland.getOwner() != secondIsland.getOwner()) throw new IllegalArgumentException();

        firstIsland.moveAllPawnsFrom(secondIsland);
        islands.remove(second);
    }




    public void changeIslandOwner(int islandIndex, Player newOwner, ProfessorManager professorManager) throws IllegalArgumentException {
        IslandTile island = islands.get(islandIndex);
        if (island.getOwner() == newOwner) throw new IllegalArgumentException();
        // check influence
        // change owner
        // if needed, mergeIslands
    }

    //TODO: testing
    //TODO: other methods
}
