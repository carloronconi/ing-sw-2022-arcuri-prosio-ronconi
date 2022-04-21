package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.studentmanagers.Bag;
import it.polimi.ingsw.model.studentmanagers.CharacterStudentCounter;
import it.polimi.ingsw.model.studentmanagers.DiningRoom;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class Princess extends Character {
    private final CharacterStudentCounter studentCounter;
    private final Bag bag;
    private final List<Player> players;

    public Princess(Bag bag, List<Player> players) {
        super(2);
        this.bag = bag;
        this.players = players;
        studentCounter = new CharacterStudentCounter();
        IntStream.range(0, 4).forEach(i -> studentCounter.takeStudentFrom(bag));
    }

    public void useEffect(PawnColor color, UUID player) throws NoSuchFieldException {
        DiningRoom diningRoom = ConverterUtility.idToElement(player, players).getDiningRoom();
        diningRoom.moveStudentFromPrincess(color, studentCounter);
        if(!isCostIncreased()) increaseCost();
        studentCounter.takeStudentFrom(bag);
    }
}
