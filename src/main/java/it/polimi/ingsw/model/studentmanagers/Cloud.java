package it.polimi.ingsw.model.studentmanagers;

import java.util.stream.IntStream;

/**
 * subclass of StudentCounter that can just be created as empty StudentCounter and can just move 3 random pawns from the bag
 */
public class Cloud extends StudentCounter {
    private final Bag bag;
    public Cloud(Bag bag){
        super();
        this.bag = bag;
    }

    /**
     * move random pawns from the bag
     * @param howManyPawns depending on the rules of the game (number of players)
     */
    public void fill(int howManyPawns){
        IntStream.range(0,howManyPawns).forEach(i -> movePawnFrom(bag));
    }
}
