package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;

import java.util.UUID;

public class Musician extends SwapperCharacter{

    /**
     * initialises super with 2 maximum color swaps
     * @param gameModel needed for special effect
     */
    public Musician(GameModel gameModel) {
        super(1, gameModel, 2);
    }

    /**
     * to be called after calling setupColorSwaps at least once and at most twice in order to make
     * the swap between the entrance and the dining room of a player
     * @param player owner of the entrance and dining room
     * @throws NoSuchFieldException if the chosen color doesn't exist
     * @throws IllegalStateException if setupColorSwaps was never called before useEffect
     */
    @Override
    public void useEffect(UUID player) throws NoSuchFieldException, IllegalStateException {
        if(colorSwaps.isEmpty()) throw new IllegalStateException("never called setupColorSwaps before using the effect: has to be called at least once");
        Entrance entrance = gameModel.getPlayerById(player).getEntrance();
        DiningRoom diningRoom = gameModel.getPlayerById(player).getDiningRoom();

        for(ColorSwap cs: colorSwaps) entrance.swapStudent(diningRoom, cs.getGive(), cs.getTake());
        if(!isCostIncreased()) increaseCost();

    }
}
