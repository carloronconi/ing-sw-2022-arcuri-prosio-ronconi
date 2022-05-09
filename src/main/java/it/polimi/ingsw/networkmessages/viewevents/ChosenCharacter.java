package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.model.charactercards.AvailableCharacter;

import java.io.Serializable;
import java.util.UUID;

public class ChosenCharacter implements Serializable, GameViewEvent {
    private final AvailableCharacter chosenCharacter;

    public ChosenCharacter(AvailableCharacter chosenCharacter){
        this.chosenCharacter = chosenCharacter;
    }

    public AvailableCharacter getChosenCharacter() {
        return chosenCharacter;
    }

}
