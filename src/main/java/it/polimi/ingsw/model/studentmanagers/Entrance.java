package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;

import java.util.List;
import java.util.stream.IntStream;

/**
 * subclass of StudentCounter that can just be filled in when initialised or with fill method
 */
public class Entrance extends StudentCounter {
    private final List<Cloud> clouds;

    /**
     * constructor to create Entrance filling it with 7 pawns from the bag
     * @param bag from which it takes the first 7 pawns
     * @param clouds from which it will be filled
     */
    public Entrance(Bag bag, List<Cloud> clouds) {
        super();
        this.clouds = clouds;
        IntStream.range(0,7).forEach(i -> movePawnFrom(bag));
    }

    /**
     * way to fill the Entrance from one of the clouds in the game
     * @param fromCloud to select the cloud from which to fill the Entrance
     */
    public void fill(int fromCloud){

        IntStream.range(0, clouds.get(fromCloud).count()).forEach(i -> movePawnFrom(clouds.get(fromCloud) ));
    }

    /**
     * method used by Juggler character card to exchange student with the entrance of a player
     * @param fromStudentCounter StudentCounter of the Juggler
     * @param given color of the student to be moved in the entrance
     * @param taken color of the student to be moved in the Juggler
     * @throws IllegalArgumentException one of the chosen colors is not available
     */
    public void swapStudent(StudentCounter fromStudentCounter, PawnColor given, PawnColor taken) throws IllegalArgumentException{
        if (fromStudentCounter.count(taken)<1 || count(given)<1) throw new IllegalArgumentException();
        fromStudentCounter.movePawnFrom(this,given);
        movePawnFrom(fromStudentCounter, taken);
    }
}
