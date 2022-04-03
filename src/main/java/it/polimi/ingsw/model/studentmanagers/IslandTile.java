package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;

public class IslandTile extends StudentCounter{
    IslandTile nextTile;
    IslandTile prevTile;
    boolean isConnectNext;
    boolean isConnectPrev;
    //TODO: Player owner;
    Entrance entrance;

    public IslandTile(Entrance entrance) {
        super();
        this.isConnectNext = false;
        this.isConnectPrev = false;
        this.entrance = entrance;
    }

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
}
