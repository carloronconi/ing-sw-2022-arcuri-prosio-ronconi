package it.polimi.ingsw.server;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

public class VirtualView implements ViewInterface, EventListener<ModelEvent> {
    @Override
    public void sendAcknowledgement() {

    }

    @Override
    public void askPlayAgain() {

    }

    @Override
    public void chooseCharacter() {

    }

    @Override
    public void chooseCloud() {

    }

    @Override
    public void getAssistantCard() {

    }

    @Override
    public void invalidAssistantCard() {

    }


    @Override
    public void getNickname() {

    }

    @Override
    public void getPreferences() {

    }

    @Override
    public void playerTurn() {

    }

    @Override
    public void invalidCharacterChoice() {

    }

    @Override
    public void invalidMNMove() {

    }

    @Override
    public void invalidNickname() {

    }

    @Override
    public void moveMotherNature() {

    }

    @Override
    public void moveStudent() {

    }

    @Override
    public void gameOver() {

    }

    @Override
    public void getColorSwap() {

    }

    @Override
    public void getColorChoice() {

    }

    @Override
    public void getIslandChoice() {

    }

    @Override
    public void update(ModelEvent modelEvent) {
        //forward the modelEvent through the socket
    }
}
