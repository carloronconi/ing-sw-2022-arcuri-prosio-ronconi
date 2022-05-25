package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.UUID;

public class SetCharacterSettings implements Serializable, GameViewEvent {
    private PawnColor color;
    private UUID player;
    private UUID island;
    private PawnColor giveColor;
    private PawnColor takeColor;

    public SetCharacterSettings(PawnColor color, UUID player, UUID island, PawnColor giveColor, PawnColor takeColor) {
        //all attributes can be null or have a value,
        // according to what interface/abstract class the chosen character implements
        this.color = color;
        this.player = player;
        this.island = island;
        this.giveColor = giveColor;
        this.takeColor = takeColor;
    }

    public PawnColor getColor() {
        return color;
    }

    public UUID getPlayer() {
        return player;
    }

    public UUID getIsland() {
        return island;
    }

    public PawnColor getGiveColor() {
        return giveColor;
    }

    public PawnColor getTakeColor() {
        return takeColor;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException, InterruptedException {
        //TODO: validate input (are some settings illegal?)
        virtualView.notifyController(this);
        virtualView.moveStudent();

    }
}
