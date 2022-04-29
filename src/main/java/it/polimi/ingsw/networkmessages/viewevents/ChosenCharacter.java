package it.polimi.ingsw.networkmessages.viewevents;

import java.io.Serializable;
import java.util.UUID;

public class ChosenCharacter implements Serializable, ViewEvent {
    private final UUID chosenCharacter;

    public ChosenCharacter(UUID chosenCharacter){
        this.chosenCharacter = chosenCharacter;
    }

    public UUID getChosenCharacter() {
        return chosenCharacter;
    }

}
