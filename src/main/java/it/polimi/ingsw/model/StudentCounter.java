package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;

public class StudentCounter {
    private final EnumMap<PawnColor, Integer> map;

    public StudentCounter() {
        this(false);
    }
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
    // moves random pawn between two StudentCounters
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

    // moves pawn of a selected PawnColor between two StudentCounters
    public void movePawnFrom(StudentCounter other, PawnColor color) throws IllegalArgumentException{
        if(other.map.get(color) <= 0) throw new IllegalArgumentException();
        other.map.put(color, other.map.get(color) - 1);
        map.put(color, map.get(color) + 1);
    }

    public int count(PawnColor color){
        return map.get(color);
    }

    public int count(){
        int sum = 0;
        for(PawnColor c : PawnColor.values()){
            sum += map.get(c);
        }
        return sum;
    }

}
