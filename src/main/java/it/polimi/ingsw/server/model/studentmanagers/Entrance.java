package it.polimi.ingsw.server.model.studentmanagers;

import it.polimi.ingsw.server.model.ConverterUtility;
import it.polimi.ingsw.server.model.PawnColor;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * subclass of StudentCounter that can just be filled in when initialised or with fill method
 */
public class Entrance extends StudentCounter {
    private final List<Cloud> clouds;

    /**
     * constructor to create Entrance filling it with numOfStudents
     * pawns from the bag: 7 if 2 players, 9 if 3 players
     * @param bag from which it takes the first 7 pawns
     * @param clouds from which it will be filled
     */
    public Entrance(Bag bag, List<Cloud> clouds, int numOfStudents) {
        super();
        this.clouds = clouds;
        IntStream.range(0,numOfStudents).forEach(i -> movePawnFrom(bag));
    }

    /**
     * way to fill the Entrance from one of the clouds in the game
     * @param whichCloud to select the cloud from which to fill the Entrance
     * @throws IllegalArgumentException if the cloud is not found in the list
     */
    public void fill(UUID whichCloud) throws NoSuchFieldException {

        int cloudIndex= ConverterUtility.idToIndex(whichCloud, clouds);

        int max = clouds.get(cloudIndex).count();
        for(int i=0; i<max; i++){
            movePawnFrom(clouds.get(cloudIndex));

        }

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
