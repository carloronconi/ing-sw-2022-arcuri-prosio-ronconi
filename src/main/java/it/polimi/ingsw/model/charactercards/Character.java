package it.polimi.ingsw.model.charactercards;

import java.util.UUID;

/**
 * Abstract class from which all character cards inherit
 */
public abstract class Character {
    /**
     * has all attributes common to all character cards:
     * cost, unique id and boolean representing if any player as ever used the card: if yes, the cost has to be increased by one
     */
    private final int cost;
    private final UUID id;
    private boolean costIncreased;

    /**
     * creates character with unique id, costIncreased set to false and cost depending on specific subclass of character
     * @param cost set by the subclasses
     */
    public Character(int cost) {
        this.cost = cost;
        id = UUID.randomUUID();
        costIncreased = false;
    }

    public boolean isCostIncreased(){
        return costIncreased;
    }

    /**
     * costIncreased can just change once from false to true, then never changed again
     */
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
