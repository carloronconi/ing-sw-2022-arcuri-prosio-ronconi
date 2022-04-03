package it.polimi.ingsw.model.studentmanagers;

import java.util.List;
import java.util.stream.IntStream;

public class Entrance extends StudentCounter {
    private final Bag bag;
    private final List<Cloud> clouds;

    public Entrance(Bag bag, List<Cloud> clouds) {
        super();
        this.bag = bag;
        this.clouds = clouds;
        IntStream.range(0,6).forEach(i -> movePawnFrom(bag));
    }

    public void fill(int fromCloud){
        IntStream.range(0, clouds.get(fromCloud).count()).forEach(i -> movePawnFrom(clouds.get(fromCloud)));
    }
}
