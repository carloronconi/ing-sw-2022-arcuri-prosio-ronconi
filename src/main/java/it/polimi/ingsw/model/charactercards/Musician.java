package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithPlayer;
import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;

import java.util.List;
import java.util.UUID;

public class Musician extends SwapperCharacter implements EffectWithPlayer {
    private UUID player;
    private static int maxColorSwaps = 2;

    /**
     * initialises super with 2 maximum color swaps
     * @param players needed for special effect
     */
    public Musician(List<Player> players) {
        super(AvailableCharacter.MUSICIAN.getInitialCost(), players, maxColorSwaps);
    }

    /**
     * to be called after calling setupColorSwaps at least once and at most twice in order to make
     * the swap between the entrance and the dining room of a player
     * @throws NoSuchFieldException if the chosen color doesn't exist
     * @throws IllegalStateException if setupColorSwaps was never called before useEffect
     */
    @Override
    public void useEffect() throws NoSuchFieldException, IllegalStateException {
        if (player == null) throw new IllegalStateException();
        if(colorSwaps.isEmpty()) throw new IllegalStateException("never called setupColorSwaps before using the effect: has to be called at least once");
        Entrance entrance = ConverterUtility.idToElement(player, players).getEntrance();
        DiningRoom diningRoom = ConverterUtility.idToElement(player, players).getDiningRoom();

        for(ColorSwap cs: colorSwaps) entrance.swapStudent(diningRoom, cs.getGive(), cs.getTake());
        if(!isCostIncreased()) increaseCost();
        player = null;
    }


    @Override
    public void setEffectPlayer(UUID player) {
        this.player = player;
    }

    public static int getMaxColorSwaps() {
        return maxColorSwaps;
    }
}
