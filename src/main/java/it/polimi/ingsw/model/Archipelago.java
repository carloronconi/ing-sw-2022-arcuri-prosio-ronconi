package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.IslandTile;

import java.util.ArrayList;

public class Archipelago {
    private final ArrayList<IslandTile> islands;

    public Archipelago(IslandTile firstIsland) {
        islands = new ArrayList<>();
        islands.add(firstIsland);
    }

    public int size(){
        return islands.size();
    }

    //TODO: need to return islands, not copy, maybe make islands public?
    public ArrayList<IslandTile> getIslands(){
        return islands;
    }

    public void add(IslandTile islandTile){
        islands.add(islandTile);
    }

    //TODO: similar getters to Island, develop iterating over all the islands and calling those getters, then returning the sum
}
