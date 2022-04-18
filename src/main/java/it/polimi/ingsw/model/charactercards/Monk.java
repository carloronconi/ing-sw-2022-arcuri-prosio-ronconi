package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.CharacterStudentCounter;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.UUID;
import java.util.stream.IntStream;

/**
 * one of the concrete subclasses of character representing a specific character card
 */
public class Monk extends Character{
    /**
     * has specific attributes needed to execute its special effect
     */
    private final CharacterStudentCounter studentCounter;
    private final Bag bag;
    private final IslandManager islandManager;

    /**
     * creates a monk with cost of 1, draws 4 students from the bag
     * @param bag bag of the game to draw students from each time students are taken by a player when using effect
     * @param islandManager needed to move student to island when effect is used
     */
    protected Monk(Bag bag, IslandManager islandManager) {
        super(1);
        this.bag = bag;
        this.islandManager = islandManager;
        studentCounter = new CharacterStudentCounter();
        IntStream.range(0,4).forEach(i -> studentCounter.takeStudentFrom(bag));
    }

    /**
     * special effect with which a player can draw a chosen student from the character card and place it on any island
     * @param color to choose the student from the monk
     * @param island destination of the student that will be moved
     */
    public void useEffect(PawnColor color, UUID island) throws NoSuchFieldException {
        islandManager.moveStudentToIsland(color, island, studentCounter);
        if (!isCostIncreased()) increaseCost();
        studentCounter.takeStudentFrom(bag);
    }
}
