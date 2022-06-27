package it.polimi.ingsw.networkmessages.controllercalls;

import it.polimi.ingsw.utilities.EventManager;
import it.polimi.ingsw.utilities.ViewInterface;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

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
