package it.polimi.ingsw.model.studentmanagers;

import java.util.stream.IntStream;

public class Cloud extends StudentCounter {
    private final Bag bag;
    public Cloud(Bag bag){
        super();
        this.bag = bag;
    }
    public void fill(){
        IntStream.range(0,3).forEach(i -> movePawnFrom(bag));
    }
}
