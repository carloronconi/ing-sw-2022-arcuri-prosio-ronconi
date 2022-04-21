package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.charactercards.Princess;

/**
 * subclass of StudentCounter that can just be filled from the entrance
 */
public class DiningRoom extends StudentCounter {
    private final Entrance entrance;

    public DiningRoom(Entrance entrance) {
        super();
        this.entrance = entrance;
    }

    /**
     * fill from entrance selecting the color of the student to be moved
     * @param color color of the student in the entrance to be moved
     */
    public void fill(PawnColor color){
        movePawnFrom(entrance,color);
    }

    public void moveStudentFromPrincess(PawnColor color, CharacterStudentCounter princessStudentCounter){
        movePawnFrom(princessStudentCounter, color);
    }
}
