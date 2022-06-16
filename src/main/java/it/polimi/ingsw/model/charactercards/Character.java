package it.polimi.ingsw.model.charactercards;

import java.util.Locale;

/**
 * Abstract class from which all character cards inherit
 */
public abstract class Character{
    /**
     * has all attributes common to all character cards:
     * cost, unique id and boolean representing if any player as ever used the card: if yes, the cost has to be increased by one
     */
    private int currentCost;
    private final AvailableCharacter characterValue;
    private boolean costIncreased;

    /**
     * creates character with unique id, costIncreased set to false and cost depending on specific subclass of character
     * @param initialCost set by the subclasses
     */
    public Character(int initialCost) {
        this.currentCost = initialCost;
        String name = this.getClass().getName();
        name = name.toUpperCase(Locale.ROOT);
        name = name.substring(name.lastIndexOf(".") +1);
        characterValue = AvailableCharacter.valueOf(name);
        costIncreased = false;
    }

    public boolean isCostIncreased(){
        return costIncreased;
    }

    /**
     * costIncreased can just change once from false to true, then never changed again
     */
    public void increaseCost(){
        if(costIncreased) throw new IllegalStateException("Cost already increased");
        costIncreased = true;
        currentCost++;
    }

    public int getCurrentCost() {
        return currentCost;
    }

    public AvailableCharacter getValue(){ return characterValue;}


    public abstract void useEffect() throws IllegalStateException, NoSuchFieldException;
}
