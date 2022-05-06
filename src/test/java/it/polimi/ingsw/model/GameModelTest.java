package it.polimi.ingsw.model;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    GameModel gameModel;

    @BeforeEach
    void setUp() {
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");
        EventManager<ModelEvent> modelEventManager = new EventManager<>();
        //create fake eventListener
        EventListener<ModelEvent> listener = modelEvent -> System.out.println("update received");
        modelEventManager.subscribe(GameState.class, listener);

        gameModel = new GameModel(false, nicknames, modelEventManager);
        assertEquals(106, gameModel.countStudentsInBag());
    }

    @Test
    void constructor_easyModeThreePlayers(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo", "Carlo");
        EventManager<ModelEvent> modelEventManager = new EventManager<>();
        //create fake eventListener
        EventListener<ModelEvent> listener = modelEvent -> System.out.println("update received");
        modelEventManager.subscribe(GameState.class, listener);

        gameModel = new GameModel(false, nicknames, modelEventManager);
        assertEquals(93, gameModel.countStudentsInBag());

    }

    @Test
    void constructor_hardModeTwoPlayers(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");
        EventManager<ModelEvent> modelEventManager = new EventManager<>();
        //create fake eventListener
        EventListener<ModelEvent> listener = modelEvent -> System.out.println("update received");
        modelEventManager.subscribe(GameState.class, listener);

        gameModel = new GameModel(true, nicknames, modelEventManager);

        Set<AvailableCharacter> availableCharacters = gameModel.getAvailableCharacterCards().keySet();
        int expected = 106;
        if (availableCharacters.contains(AvailableCharacter.MONK)) expected-=4;
        if (availableCharacters.contains(AvailableCharacter.JUGGLER)) expected-=6;
        if (availableCharacters.contains(AvailableCharacter.PRINCESS)) expected-=4;
        assertEquals(expected, gameModel.countStudentsInBag());

    }

    @Test
    void moveStudentToDining() {
        UUID playerId = gameModel.getPlayerIds().get(0);
        EnumMap<PawnColor, Integer> colorsInEntrance = gameModel.getEntrances().get(playerId);
        PawnColor color = null;
        int studentsInEntranceBefore = 0;
        for (PawnColor c : colorsInEntrance.keySet()) {
            if (colorsInEntrance.get(c) > 0) {
                color = c;
                studentsInEntranceBefore = colorsInEntrance.get(c);
                break;
            }
        }
        EnumMap<PawnColor, Integer> colorsInDining = gameModel.getDiningRooms().get(playerId);
        int studentsInDiningBefore = colorsInDining.get(color);
        try {
            gameModel.moveStudentToDining(color, playerId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        colorsInEntrance = gameModel.getEntrances().get(playerId);
        colorsInDining = gameModel.getDiningRooms().get(playerId);
        assertEquals(studentsInEntranceBefore - 1 , colorsInEntrance.get(color));
        assertEquals(studentsInDiningBefore + 1, colorsInDining.get(color));
    }



    @Test
    void moveCloudToEntrance() {
    }

    @Test
    void playAssistantCard() {
    }

    @Test
    void getNumOfTowers() {
    }

    @Test
    void moveStudentToIsland() {
    }

    @Test
    void fillAllClouds() {
    }

    @Test
    void countIslands() {
    }

    @Test
    void payAndGetCharacter() {
    }

    @Test
    void moveMotherNature() {
    }
}