package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;

/**
 * subclass of StudentCounter that has attributes to reach the two neighbor tiles and attributes to
 * know if it is connected to those neighbor tiles or not
 */
public class IslandTile extends StudentCounter{
    IslandTile nextTile;
    IslandTile prevTile;
    boolean isConnectNext;
    boolean isConnectPrev;
    //TODO: Player owner;
    Entrance entrance;

    /**
     * constructor initializes with no connections to neighbor tiles
     * @param entrance needed to move students
     */
    public IslandTile(Entrance entrance) {
        super();
        this.isConnectNext = false;
        this.isConnectPrev = false;
        this.entrance = entrance;
    }

    /**
     * initial setup of the tile needed to fill the neighbor tiles attributes
     * @param next next tile
     * @param prev previous tile
     */
    public void link(IslandTile next, IslandTile prev){
        this.nextTile = next;
        this.prevTile = prev;
    }

    public void connectNext(){
        isConnectNext = true;
    }

    public void connectPrev(){
        isConnectPrev = true;
    }

    public boolean isConnectNext() {
        return isConnectNext;
    }

    public boolean isConnectPrev() {
        return isConnectPrev;
    }


    public void fill(PawnColor color){
        movePawnFrom(entrance,color);
    }

    //TODO: getter and setter for owner
    //TODO: all methods should throw exception if dindn't set up nextTile and prevTile yet?
}
