package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class SetColorSwap implements Serializable, GameViewEvent {
    private final PawnColor give;
    private final PawnColor take;

    public SetColorSwap(PawnColor give, PawnColor take) {
        this.give = give;
        this.take = take;
    }

    public PawnColor getGive() {
        return give;
    }

    public PawnColor getTake() {
        return take;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {

    }
}
