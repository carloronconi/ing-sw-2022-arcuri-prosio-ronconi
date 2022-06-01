package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithColor;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithIsland;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.CharacterStudentCounter;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * one of the concrete subclasses of character representing a specific character card
 */
public class Monk extends Character implements EffectWithColor, EffectWithIsland {
    /**
     * has specific attributes needed to execute its special effect
     */
    private static final CharacterStudentCounter studentCounter = new CharacterStudentCounter();
    private final Bag bag;
    private final IslandManager islandManager;
    private PawnColor color;
    private UUID island;

    /**
     * creates a monk with cost of 1, draws 4 students from the bag
     * @param bag bag of the game to draw students from each time students are taken by a player when using effect
     * @param islandManager needed to move student to island when effect is used
     */
    protected Monk(Bag bag, IslandManager islandManager) {
        super(AvailableCharacter.MONK.getInitialCost());
        this.bag = bag;
        this.islandManager = islandManager;
        IntStream.range(0,4).forEach(i -> studentCounter.takeStudentFrom(bag));
    }

    public static ArrayList<PawnColor> getStudents() {
        ArrayList<PawnColor> colors = new ArrayList<>();
        for (PawnColor color : PawnColor.values()){
            for (int i = 0; i<studentCounter.count(color); i++){
                colors.add(color);
            }
        }
        return colors;
    }

    /**
     * special effect with which a player can draw a chosen student from the character card and place it on any island
     */
    public void useEffect() throws NoSuchFieldException {
        if (color==null || island==null) throw new IllegalStateException();
        islandManager.moveStudentToIsland(color, island, studentCounter);
        if (!isCostIncreased()) increaseCost();
        studentCounter.takeStudentFrom(bag);
        color = null;
        island = null;
    }

    /**
     * this method returns true if the input color is contained by the monk
     * @param color is the color of the student we want to know if it is contained
     * @return true if the input color is contained by the monk otherwise returns false
     */
    public boolean isColorContained(PawnColor color){
        return studentCounter.count(color) > 0;
    }

    @Override
    public void setEffectColor(PawnColor color) {
        this.color = color;
    }

    @Override
    public void setEffectIsland(UUID island) {
        this.island = island;
    }
}
