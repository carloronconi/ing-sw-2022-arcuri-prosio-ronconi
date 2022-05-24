package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.UUID;

public class MovedStudent implements Serializable, GameViewEvent {
    private final PawnColor color;
    private final UUID islandId;

    public MovedStudent(PawnColor color, UUID islandId){
        this.color=color;
        this.islandId = islandId; //null if the student has to be moved to the entrance
    }

    public PawnColor getColor(){
        return color;
    }

    public UUID getIslandId() {
        return islandId;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {
        //TODO: react if move is invalid with new message (never created class)
        virtualView.notifyController(this);
        virtualView.moveMotherNature();

    }
}
