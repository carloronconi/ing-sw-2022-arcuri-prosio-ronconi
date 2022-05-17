package it.polimi.ingsw;

import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

public interface ViewInterface extends EventListener<ModelEvent> {

    void sendAcknowledgement();

    void askPlayAgain();

    void chooseCharacter();

    void chooseCloud();

    void getAssistantCard();

    void invalidAssistantCard();

    void getNickname();

    void getPreferences();

   // void letsPlay();

    void playerTurn();

    void invalidCharacterChoice();

    void invalidMNMove();

    void invalidNickname();

    void moveMotherNature();

    void moveStudent();

    void gameOver();

    void getColorSwap();

    void getColorChoice();

    void getIslandChoice();

}
