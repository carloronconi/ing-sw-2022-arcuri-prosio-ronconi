package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.Archipelago;
import it.polimi.ingsw.model.PawnColor;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Class used to manage islands and the merging of islands in archipelagos
 */
public class IslandManager extends StudentCounter {
    private final ArrayList<IslandTile> islands;
    private final ArrayList<Archipelago> archipelagos;
    private int motherNaturePosition;

    /**
     * Constructor draws 2 tiles for each color from the bag, then adds 12 islands and puts in the first 10
     * islands 2 random student pawns, then creates one archipelago for each island and adds it to archipelagos
     * @param bag needed to draw the 10 students to initialize the islands
     */
    public IslandManager(Bag bag) {
        super();
        motherNaturePosition = 0;
        for (PawnColor c: PawnColor.values()) {
            movePawnFrom(bag, c);
            movePawnFrom(bag, c);
        }
        archipelagos = new ArrayList<>();
        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            IslandTile it = new IslandTile();
            if (i % 6 != 0) it.movePawnFrom(this);
            islands.add(it);
            Archipelago archipelago = new Archipelago(it);
            archipelagos.add(archipelago);
        }
    }

    //TODO: separate method to check merge? or merge automatically when two neighbor islands have same owner?
    public void mergeArchipelagos(int first, int second) {
        for (IslandTile i : archipelagos.get(second).getIslands()){
            archipelagos.get(first).add(i);
        }
        archipelagos.remove(second);
    }

    //TODO: testing
    //TODO: other methods
}
