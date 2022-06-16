package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.model.charactercards.SwapperCharacter;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithColor;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithIsland;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithPlayer;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
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
