package it.polimi.ingsw.server.model.charactercards;

import it.polimi.ingsw.server.model.ConverterUtility;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.charactercards.effectarguments.EffectWithPlayer;
import it.polimi.ingsw.server.model.studentmanagers.Bag;
import it.polimi.ingsw.server.model.studentmanagers.CharacterStudentCounter;
import it.polimi.ingsw.server.model.studentmanagers.Entrance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class Juggler extends SwapperCharacter implements EffectWithPlayer {
    private static final CharacterStudentCounter studentCounter = new CharacterStudentCounter();
    private UUID player;
    private GameModel gameModel;

    private static final int maxColorSwaps = 3;

    /**
     * initialises parameters needed for effect, draws 6 students from the bag and initialises super with 3 maximum color swaps
     * @param players needed for special effect
     * @param bag needed to draw students
     */
    public Juggler(Bag bag, List<Player> players, GameModel gameModel) {
        super(AvailableCharacter.JUGGLER.getInitialCost(), players, maxColorSwaps);
        this.gameModel = gameModel;
        IntStream.range(0,6).forEach(i -> studentCounter.takeStudentFrom(bag));
    }

    /**
     * to be called after calling setupColorSwaps at least once and at most three times in order to make the
     * swap with the Juggler
     * @throws IllegalStateException when never called setupColorSwap
     */
    public void useEffect() throws IllegalStateException, NoSuchFieldException {
        if (player == null) throw new IllegalStateException();
        if(colorSwaps.isEmpty()) /*throw new IllegalStateException("never called setupColorSwaps before using the effect: has to be called at least once");*/ return;
        Entrance entrance = ConverterUtility.idToElement(player, players).getEntrance();
        for(ColorSwap cs : colorSwaps) entrance.swapStudent(this.studentCounter, cs.getGive(), cs.getTake());
        if (!isCostIncreased()) increaseCost();
        player = null;
        if (gameModel!=null) gameModel.notifyListeners();
    }

    /**
     * this method returns true if the input color is contained by the juggler
     * @param color is the color of the student we want to know if it is contained
     * @return true if the input color is contained by the juggler otherwise returns false
     */
    public boolean isColorContained(PawnColor color){
        return studentCounter.count(color) > 0;
    }

    @Override
    public void setEffectPlayer(UUID player) {
        this.player = player;
    }

    public static int getMaxColorSwaps() {
        return maxColorSwaps;
    }

    public static ArrayList<PawnColor> getStudents(){
        ArrayList<PawnColor> colors = new ArrayList<>();
        for (PawnColor color : PawnColor.values()){
            for (int i = 0; i<studentCounter.count(color); i++){
                colors.add(color);
            }
        }
        return colors;
    }
}
