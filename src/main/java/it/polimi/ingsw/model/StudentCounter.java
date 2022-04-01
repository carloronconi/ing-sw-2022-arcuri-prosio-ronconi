package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;

/**
 * Class StudentCounter counts the number of student tiles for each PawnColor
 */
public class StudentCounter {
    private final EnumMap<PawnColor, Integer> map;

    /**
     * Constructor to create studentCounter with 0 pawns for each color
     */
    public StudentCounter() {
        this(false);
    }

    /**
     * Constructor to create studentCounter with 26 pawns for each color
     * @param full indicates if to initialize the studentCounter with 130 students (26 of each color) or with 0 students
     */
    public StudentCounter(boolean full) {
        map = new EnumMap<>(PawnColor.class);
        if(!full) { // initialize map with zero students
            for(PawnColor color : PawnColor.values()){
                map.put(color, 0);
            }
        } else { // initialise map with 26 students per color
            for(PawnColor color : PawnColor.values()){
                map.put(color, 26);
            }
        }
    }

    // TODO: create custom exception?
    // TODO: javadoc

    /**
     * Moves one student of random color pawn (among those with value > 0) from other studentCounter to this
     * @param other studentCounter to take one student pawn from
     * @return random color of the moved student
     * @throws IllegalArgumentException if no color of the other studentCounter has number > 0, would be impossible to move a pawn
     */
    public PawnColor movePawnFrom(StudentCounter other) throws IllegalArgumentException {
        ArrayList<PawnColor> movableColors = new ArrayList<>();
        for(PawnColor color : PawnColor.values()){
            if(other.map.get(color) > 0){
                movableColors.add(color);
            }
        }
        if(movableColors.size() == 0) throw new IllegalArgumentException();

        Random rand = new Random();
        int i = rand.nextInt(movableColors.size());
        PawnColor c = PawnColor.values()[i];
        movePawnFrom(other, c);
        return c;
    }

    /**
     * Moves one student of chosen color pawn (among those with value > 0) from other studentCounter to this
     * @param other studentCounter to take one student pawn from
     * @param color chosen color of the student pawn to be moved
     * @throws IllegalArgumentException if no student of the chosen color is available in other studentCounter to be moved
     */
    public void movePawnFrom(StudentCounter other, PawnColor color) throws IllegalArgumentException{
        if(other.map.get(color) <= 0) throw new IllegalArgumentException();
        other.map.put(color, other.map.get(color) - 1);
        map.put(color, map.get(color) + 1);
    }

    /**
     * Counts how many students are present for a given color
     * @param color color of pawns to be counted
     * @return number of pawns of chosen color
     */
    public int count(PawnColor color){
        return map.get(color);
    }

    /**
     * Counts total amount of students in the studentCounter
     * @return sum of number of students of each color in PawnColor
     */
    public int count(){
        int sum = 0;
        for(PawnColor c : PawnColor.values()){
            sum += map.get(c);
        }
        return sum;
    }

}
