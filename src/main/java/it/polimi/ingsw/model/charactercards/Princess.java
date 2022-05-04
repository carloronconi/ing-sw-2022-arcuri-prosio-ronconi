package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithColor;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithPlayer;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.CharacterStudentCounter;
import it.polimi.ingsw.model.studentmanagers.DiningRoom;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class Princess extends Character implements EffectWithPlayer, EffectWithColor {
    private final CharacterStudentCounter studentCounter;
    private final Bag bag;
    private final List<Player> players;
    private UUID player;
    private PawnColor color;

    public Princess(Bag bag, List<Player> players) {
        super(2);
        this.bag = bag;
        this.players = players;
        studentCounter = new CharacterStudentCounter();
        IntStream.range(0, 4).forEach(i -> studentCounter.takeStudentFrom(bag));
    }

    public void useEffect() throws NoSuchFieldException {
        if (player == null || color == null) throw new IllegalStateException();
        DiningRoom diningRoom = ConverterUtility.idToElement(player, players).getDiningRoom();
        diningRoom.moveStudentFromPrincess(color, studentCounter);
        if(!isCostIncreased()) increaseCost();
        studentCounter.takeStudentFrom(bag);
        player = null;
        color = null;
    }


    /**
     * this method returns true if the input color is contained by the princess
     * @param color is the color of the student we want to know if it is contained
     * @return true if the input color is contained by the princess otherwise returns false
     */
    public boolean isColorContained(PawnColor color){
        return studentCounter.count(color) > 0;
    }

    @Override
    public void setEffectColor(PawnColor color) {
        this.color = color;
    }

    @Override
    public void setEffectPlayer(UUID player) {
        this.player = player;
    }
}
