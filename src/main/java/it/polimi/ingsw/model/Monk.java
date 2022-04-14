package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.CharacterStudentCounter;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.UUID;
import java.util.stream.IntStream;

public class Monk extends Character{
    private final CharacterStudentCounter studentCounter;
    private final Bag bag;
    private final IslandManager islandManager;

    public Monk(Bag bag, IslandManager islandManager) {
        super(1);
        this.bag = bag;
        this.islandManager = islandManager;
        studentCounter = new CharacterStudentCounter();
        IntStream.range(0,4).forEach(i -> studentCounter.takeStudentFrom(bag));
    }

    public void useEffect(PawnColor color, UUID island){
        islandManager.moveStudentToIsland(color, island, studentCounter);
        if (!isCostIncreased()) assertCostIncreased();
        studentCounter.takeStudentFrom(bag);
    }
}
