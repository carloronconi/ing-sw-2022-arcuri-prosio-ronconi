package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;

/**
 * subclass of StudentCounter that has attributes to reach the two neighbor tiles and attributes to
 * know if it is connected to those neighbor tiles or not
 */
public class IslandTile extends StudentCounter{

    /**
     * initializes with 0 students
     */
    public IslandTile() {
        super();
    }

    /**
     * initializes with 2 random students from the islandManager
     * @param manager IslandManager that is initializing the tile
     */
    public IslandTile(IslandManager manager){
        super();
        movePawnFrom(manager);
        movePawnFrom(manager);
    }

}
