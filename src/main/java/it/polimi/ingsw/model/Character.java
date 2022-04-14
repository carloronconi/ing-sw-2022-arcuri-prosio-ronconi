package it.polimi.ingsw.model;

import java.util.UUID;

public abstract class Character {
    private final int cost;
    private final UUID id;
    private boolean costIncreased;

    public Character(int cost) {
        this.cost = cost;
        id = UUID.randomUUID();
        costIncreased = false;
    }

    public boolean isCostIncreased(){
        return costIncreased;
    }

    public void assertCostIncreased(){
        costIncreased = true;
    }

    public int getCost() {
        return cost;
    }

    public UUID getId() {
        return id;
    }
}
