package it.polimi.ingsw.networkmessages.controllercalls;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.GameViewEvent;

import java.io.Serializable;

public class GetCharacterSettings implements Serializable, RemoteMethodCall {
    private AvailableCharacter forCharacter;

    public GetCharacterSettings(AvailableCharacter forCharacter) {
        this.forCharacter = forCharacter;
    }

    @Override
    public void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager) {
        viewInterface.getCharacterSettings(forCharacter);
    }
}
