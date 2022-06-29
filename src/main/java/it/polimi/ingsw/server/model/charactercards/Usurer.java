package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.charactercards.effectarguments.EffectWithColor;
import it.polimi.ingsw.server.model.studentmanagers.Bag;
import it.polimi.ingsw.server.model.studentmanagers.IllegalStudentMoveException;

import java.util.List;

public class Usurer extends Character implements EffectWithColor {
    private final List<Player> players;
    private final Bag bag;
    private PawnColor color;

    public Usurer(List<Player> players, Bag bag) {
        super(AvailableCharacter.USURER.getInitialCost());
        this.players = players;
        this.bag = bag;
    }

    /**
     * take 3 students from each player's dining room and put it back in the bag
     */
    public void useEffect(){
        if (color==null) throw new IllegalStateException();
        for(Player p : players){
            for (int i = 0; i < 3; i++) {
                try{
                    bag.movePawnFromDiningRoom(color,p.getDiningRoom());
                } catch (IllegalStudentMoveException e){
                    // do nothing: it's ok because a player could have less than 3 students of that color
                }
            }

        }
        if (!isCostIncreased()) increaseCost();
        color = null;

    }

    @Override
    public void setEffectColor(PawnColor color) {
        this.color = color;
    }
}
