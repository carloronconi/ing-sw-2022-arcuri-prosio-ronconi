package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class ChosenCharacter implements Serializable, GameViewEvent {
    private final AvailableCharacter chosenCharacter;

    public ChosenCharacter(AvailableCharacter chosenCharacter){
        this.chosenCharacter = chosenCharacter;
    }

    public AvailableCharacter getChosenCharacter() {
        return chosenCharacter;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {
        if (chosenCharacter == null){
            virtualView.moveStudent();
            return;
        }

        if(virtualView.isCharacterCardIllegal(chosenCharacter)){
            virtualView.invalidCharacterChoice();
            return;
        }

        virtualView.notifyController(this);

        virtualView.getCharacterSettings(chosenCharacter);

    }
}
