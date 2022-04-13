package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;

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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }



    public void moveAllPawnsFrom(IslandTile otherIsland){
        size++;
        for (PawnColor c: PawnColor.values()) {
            for (int i = 0; i < otherIsland.count(c); i++) {
                movePawnFrom(otherIsland,c);
            }
        }
    }

}
