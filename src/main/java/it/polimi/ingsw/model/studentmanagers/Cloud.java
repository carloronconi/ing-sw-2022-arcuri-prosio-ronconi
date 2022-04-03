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
     * move 3 random pawns from the bag
     */
    public void fill(){
        IntStream.range(0,3).forEach(i -> movePawnFrom(bag));
    }
}
