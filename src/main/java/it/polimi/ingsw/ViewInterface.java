package it.polimi.ingsw;

import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

import java.util.UUID;

public interface ViewInterface extends EventListener<ModelEvent> {

    void sendAcknowledgement();

    void askPlayAgain();

    void chooseCharacter();

    void chooseCloud();

    void getAssistantCard();

    void invalidAssistantCard();

    void getNickname();

    void getPreferences();

    void letsPlay();

    void playerTurn();

    void invalidCharacterChoice();

    void invalidMNMove();

    void invalidNickname();

    void invalidStudentMove();

    void moveMotherNature();

    void moveStudent();

    void gameOver(UUID winner);

    void getCharacterSettings(AvailableCharacter forCharacter);

}
