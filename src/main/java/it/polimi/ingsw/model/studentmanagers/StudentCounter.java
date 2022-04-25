package it.polimi.ingsw.model.studentmanagers;

import it.polimi.ingsw.model.PawnColor;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;

/**
 * Abstract class StudentCounter counts the number of student tiles for each PawnColor, cant be instantiated, just its
 * subclassess will be
 */
public abstract class StudentCounter {
    private final EnumMap<PawnColor, Integer> map;

    /**
     * Constructor to create studentCounter with 0 pawns for each color
     */
    public StudentCounter() {
        this(0);
    }

    /**
     * Constructor to create studentCounter with 26 pawns for each color
     * @param n indicates the number of students of each color to initialise the counter
     */
    public StudentCounter(int n) {
        map = new EnumMap<>(PawnColor.class);
        for(PawnColor color : PawnColor.values()){
            map.put(color, n);
        }
    }

    // TODO: create custom exception?

    /**
     * Moves one student of random color pawn (among those with value > 0) from other studentCounter to this, protected to only allow
     * its subclasses in the package to access it
     * @param other studentCounter to take one student pawn from
     * @return random color of the moved student
     * @throws IllegalArgumentException if no color of the other studentCounter has number > 0, would be impossible to move a pawn
     */
    protected PawnColor movePawnFrom(StudentCounter other) throws IllegalArgumentException {
        ArrayList<PawnColor> movableColors = new ArrayList<>();
        for(PawnColor color : PawnColor.values()){  //da cambiare forsa il metodo values con il metodo keySet perché values prende i valori mentre color sono le key
            if(other.map.get(color) > 0){
                movableColors.add(color);
            }
        }
        if(movableColors.size() == 0) throw new IllegalArgumentException();

        Random rand = new Random();
        int i = rand.nextInt(movableColors.size());
        PawnColor c = movableColors.get(i);
        movePawnFrom(other, c);
        return c;
    }

    /**
     * Moves one student of chosen color pawn (among those with value > 0) from other studentCounter to this, protected to only allow
     *      * its subclasses in the package to access it
     * @param other studentCounter to take one student pawn from
     * @param color chosen color of the student pawn to be moved
     * @throws IllegalArgumentException if no student of the chosen color is available in other studentCounter to be moved
     */
    protected void movePawnFrom(StudentCounter other, PawnColor color) throws IllegalArgumentException{
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

    @Override
    public String toString() {
        String s = "";
        for (PawnColor c : PawnColor.values()){
            s+= c.name() + " = " + count(c) + "; ";
        }
        return s;
    }
}


