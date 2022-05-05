package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.model.PawnColor;

import java.io.Serializable;

public class SetColorChoice implements Serializable, GameViewEvent {
    private final PawnColor color;

    public SetColorChoice(PawnColor color) {
        this.color = color;
    }

    public PawnColor getColor() {
        return color;
    }
}
