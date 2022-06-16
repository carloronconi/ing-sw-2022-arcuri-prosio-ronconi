package it.polimi.ingsw.networkmessages.controllercalls;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

import java.io.Serializable;

public class InvalidStudentMove implements Serializable, RemoteMethodCall{
    @Override
    public void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager) {
        viewInterface.invalidStudentMove();
    }
}
