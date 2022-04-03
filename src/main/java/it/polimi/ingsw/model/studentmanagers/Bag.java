package it.polimi.ingsw.model.studentmanagers;

/**
 * subclass of StudentCounter that can just be initialized, can't move pawns from any other StudentCounter,
 * other studentCounters will move pawns from it
 */
public class Bag extends StudentCounter {
    /**
     * initialize bag with 26 pawns of each color
     */
    public Bag() {
        super(26);
    }
}
