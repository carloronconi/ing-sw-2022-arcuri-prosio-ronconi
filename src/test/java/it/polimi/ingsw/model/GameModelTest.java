package it.polimi.ingsw.model;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        modelEventManager.subscribe(listener);

        gameModel = new GameModel(false, nicknames, modelEventManager);
        assertEquals(106, gameModel.countStudentsInBag());
    }

    @Test
    void constructor_easyModeThreePlayers(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo", "Carlo");
        EventManager<ModelEvent> modelEventManager = new EventManager<>();
        //create fake eventListener
        EventListener<ModelEvent> listener = modelEvent -> System.out.println("update received");
        modelEventManager.subscribe(listener);

        gameModel = new GameModel(false, nicknames, modelEventManager);
        assertEquals(93, gameModel.countStudentsInBag());

    }

    @Test
    void constructor_hardModeTwoPlayers(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");
        EventManager<ModelEvent> modelEventManager = new EventManager<>();
        //create fake eventListener
        EventListener<ModelEvent> listener = modelEvent -> System.out.println("update received");
        modelEventManager.subscribe(listener);

        gameModel = new GameModel(true, nicknames, modelEventManager);

        Set<AvailableCharacter> availableCharacters = gameModel.getAvailableCharacterCards().keySet();
        int expected = 106;
        if (availableCharacters.contains(AvailableCharacter.MONK)) expected-=4;
        if (availableCharacters.contains(AvailableCharacter.JUGGLER)) expected-=6;
        if (availableCharacters.contains(AvailableCharacter.PRINCESS)) expected-=4;
        assertEquals(expected, gameModel.countStudentsInBag());

    }

    @Test
    void constructor_hardModeThreePlayers(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo", "Carlo");
        EventManager<ModelEvent> modelEventManager = new EventManager<>();
        //create fake eventListener
        EventListener<ModelEvent> listener = modelEvent -> System.out.println("update received");
        modelEventManager.subscribe(listener);

        gameModel = new GameModel(true, nicknames, modelEventManager);

        Set<AvailableCharacter> availableCharacters = gameModel.getAvailableCharacterCards().keySet();
        int expected = 93;
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
        UUID cloud = gameModel.getClouds().keySet().stream().findFirst().get();
        UUID player = gameModel.getPlayerIds().get(0);

        int entranceBefore = 0;
        EnumMap<PawnColor, Integer> entranceB = gameModel.getEntrances().get(player);
        for (PawnColor c: PawnColor.values()){
            entranceBefore += entranceB.get(c);
        }
        try {
            gameModel.moveCloudToEntrance(cloud, player);
        } catch (NoSuchFieldException e) {
            fail();
        }
        int entranceAfter = 0;
        EnumMap<PawnColor, Integer> entranceA = gameModel.getEntrances().get(player);
        for (PawnColor c: PawnColor.values()){
            entranceAfter += entranceA.get(c);
        }
        //probaby the issue is that the entrance is already full?
        assertEquals(entranceBefore + 3, entranceAfter);
    }

    @Test
    void isAssistantCardIllegal() {
        ArrayList<UUID> idList = gameModel.getPlayerIds();
        try {
            gameModel.playAssistantCard(idList.get(0), 1);
        } catch (NoSuchFieldException e) {
            fail();
        }

        boolean isIllegal = false;
        try {
            isIllegal = gameModel.isAssistantCardIllegal(idList.get(1), 1);
        } catch (NoSuchFieldException e) {
            fail();
        }
        assertEquals(true, isIllegal);
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
    void moveMotherNature_2studentsVs1student() {

        ArrayList<Player> players = gameModel.getPlayers();
        PawnColor color0 = null;
        PawnColor color1 = null;
        UUID islandWithColor0 = null;
        int steps = 1;

        ArrayList<PawnColor> colors = new ArrayList<>(List.of(PawnColor.values()));
        System.out.println(colors);


        for (PawnColor color : colors){
            if(players.get(0).getEntrance().count(color)>1){
                color0 = color;
                break;
            }
        }
        colors.remove(color0);


        for (PawnColor color : colors){
            if (players.get(1).getEntrance().count(color)>1){
                color1 = color;
                break;
            }
        }

        try {
            gameModel.moveStudentToDining(color0, players.get(0).getId());
            gameModel.moveStudentToDining(color1, players.get(1).getId());
            assertEquals(gameModel.getProfessorOwners().get(color0), players.get(0).getId()); //player 0 owns professor of color0
            assertEquals(gameModel.getProfessorOwners().get(color1), players.get(1).getId()); //player 1 owns professor of color1
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }

        LinkedHashMap<UUID, ArrayList<PawnColor>> islands = gameModel.getIslands();
        UUID currentIsland = islands.keySet().iterator().next();
        islands.remove(currentIsland);
        Iterator<Map.Entry<UUID, ArrayList<PawnColor>>> islandsItr = islands.entrySet().iterator();

        while (islandsItr.hasNext()){
            Map.Entry<UUID, ArrayList<PawnColor>> island = islandsItr.next();
            if (island.getValue().contains(color0)){
                islandWithColor0 = island.getKey();
                break;
            }
            steps++;
        }


        try {
            gameModel.playAssistantCard(players.get(0).getId(), 10);
            gameModel.moveStudentToIsland(color1, players.get(1).getId(), islandWithColor0); //island now has 1 pawn of each color
            gameModel.moveStudentToIsland(color0, players.get(0).getId(), islandWithColor0); //island now has 2 pawns of color0 and 1 of color1
            gameModel.moveMotherNature(steps, players.get(0).getId()); //now we move MN to islandWithColor0
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(gameModel.getIslandOwners().get(islandWithColor0), players.get(0).getId());

    }
}