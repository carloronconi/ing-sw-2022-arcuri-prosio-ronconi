package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.DiningRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Usurer extends Character{
    private final List<Player> players;
    private final Bag bag;

    public Usurer(List<Player> players, Bag bag) {
        super(3);
        this.players = players;
        this.bag = bag;
    }

    /**
     * take 3 students from each player's dining room and put it back in the bag
     * @param color of the 3 students to be moved
     */
    public void useEffect(PawnColor color){
        for(Player p : players){
            for (int i = 0; i < 3; i++) {
                try{
                    bag.movePawnFromDiningRoom(color,p.getDiningRoom());
                } catch (IllegalArgumentException e){
                    // do nothing: it's ok because a player could have less than 3 students of that color
                }
            }

        }


    }
}
