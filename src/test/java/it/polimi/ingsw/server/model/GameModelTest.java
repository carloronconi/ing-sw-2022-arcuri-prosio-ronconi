package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.utilities.EventListener;
import it.polimi.ingsw.utilities.EventManager;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.studentmanagers.Bag;
import it.polimi.ingsw.server.model.studentmanagers.Cloud;
import it.polimi.ingsw.server.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.server.model.studentmanagers.Entrance;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {
    //GameModel gameModel;
    private EventManager<ModelEvent> modelEventManager;
    private EventListener<ModelEvent> listener;

    @Before
    public void setUp() {
        modelEventManager = new EventManager<>();
        //create fake eventListener
        listener = modelEvent -> System.out.println("update received");
        modelEventManager.subscribe(listener);


    }

    /**
     * this method verifies that the constructor works correctly with two players in easy mode
     */
    @Test
    public void constructor_easyModeTwoPlayers(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);
        assertEquals(106, gameModel.countStudentsInBag());

    }

    /**
     * this method verifies that the constructor works correctly with three players in easy mode
     */
    @Test
    public void constructor_easyModeThreePlayers(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo", "Carlo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);
        assertEquals(93, gameModel.countStudentsInBag());

    }

    @Test
    public void constructor_hardModeTwoPlayers(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(true, nicknames, modelEventManager);

        Set<AvailableCharacter> availableCharacters = gameModel.getAvailableCharacterCards().keySet();
        int expected = 106;
        if (availableCharacters.contains(AvailableCharacter.MONK)) expected-=4;
        if (availableCharacters.contains(AvailableCharacter.JUGGLER)) expected-=6;
        if (availableCharacters.contains(AvailableCharacter.PRINCESS)) expected-=4;
        assertEquals(expected, gameModel.countStudentsInBag());

    }

    /**
     * this method verifies that the constructor works correctly with three players in hard mode
     */
    @Test
    public void constructor_hardModeThreePlayers(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo", "Carlo");

        GameModel gameModel = new GameModel(true, nicknames, modelEventManager);

        Set<AvailableCharacter> availableCharacters = gameModel.getAvailableCharacterCards().keySet();
        int expected = 93;
        if (availableCharacters.contains(AvailableCharacter.MONK)) expected-=4;
        if (availableCharacters.contains(AvailableCharacter.JUGGLER)) expected-=6;
        if (availableCharacters.contains(AvailableCharacter.PRINCESS)) expected-=4;
        assertEquals(expected, gameModel.countStudentsInBag());

    }

    /**
     * this method verifies that the moveStudentToDining method works correctly
     */
    @Test
    public void moveStudentToDining() {
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo", "Carlo");


        GameModel gameModel;

        UUID playerId = null;
        PawnColor color = null;
        int studentsInEntranceBefore = 0;
        EnumMap<PawnColor, Integer> colorsInEntrance = null;

        boolean found = false;
        do {
            gameModel = new GameModel(false, nicknames, modelEventManager);

            playerId = gameModel.getPlayerIds().get(0);
            colorsInEntrance = gameModel.getEntrances().get(playerId);
            color = null;
            studentsInEntranceBefore = 0;
            for (PawnColor c : colorsInEntrance.keySet()) {
                if (colorsInEntrance.get(c) > 2) {
                    color = c;
                    studentsInEntranceBefore = colorsInEntrance.get(c);
                    found = true;
                    break;
                }
            }

        } while (!found);


        EnumMap<PawnColor, Integer> colorsInDining = gameModel.getDiningRooms().get(playerId);
        int studentsInDiningBefore = colorsInDining.get(color);

        for (int i=0; i<3; i++){
            try {
                gameModel.moveStudentToDining(color, playerId);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        colorsInEntrance = gameModel.getEntrances().get(playerId);
        colorsInDining = gameModel.getDiningRooms().get(playerId);
        assertEquals(studentsInEntranceBefore - 3, colorsInEntrance.get(color));
        assertEquals(studentsInDiningBefore + 3, colorsInDining.get(color));
    }


    /**
     * this method verifies that the moveCloudToEntrance method works correctly
     */
    @Test
    public void moveCloudToEntrance() {
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        UUID cloud = gameModel.getClouds().keySet().stream().findFirst().get();
        UUID player = gameModel.getPlayerIds().get(0);

        int entranceBefore = 0;
        EnumMap<PawnColor, Integer> entranceB = gameModel.getEntrances().get(player);
        for (PawnColor c: PawnColor.values()){
            entranceBefore += entranceB.get(c);
        }
        gameModel.fillAllClouds();
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

        assertEquals(entranceBefore + 3, entranceAfter);
    }

    /**
     * this method verifies that the isAssistantCardIllegal method works correctly
     */
    @Test
    public void isAssistantCardIllegal() {
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo", "Carlo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

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


    /**
     * this method verifies that the playAssistantCard method works correctly
     */
    @Test
    public void playAssistantCard() {
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        assertEquals(10, gameModel.getPlayers().get(0).getDeckSize());

        try {
            gameModel.playAssistantCard(gameModel.getPlayerIds().get(0), 5);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assertEquals(9, gameModel.getPlayers().get(0).getDeckSize());

    }

    /**
     * this method verifies that the getNumOfTowers method throws an exception if the player is different
     */
    @Test (expected = RuntimeException.class)
    public void getNumOfTowers_exception() {
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        Bag bag = new Bag();
        ArrayList<Cloud> clouds = new ArrayList<>();
        Entrance entrance = new Entrance(bag, clouds, 7);
        DiningRoom diningRoom = new DiningRoom(entrance);
        Player player = new Player(entrance, diningRoom, "paperino");
        gameModel.getNumOfTowers(player.getId());

    }

    /**
     * this method verifies that the moveStudentToIsland method works correctly
     */
    @Test
    public void moveStudentToIsland() {
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        ArrayList<UUID> islandIds = new ArrayList<>();
        for (UUID id : gameModel.getIslands().keySet()){
            islandIds.add(id);
        }

        assertEquals(0, gameModel.getIslands().get(islandIds.get(0)).size());

        PawnColor c = null;
        for (PawnColor color : PawnColor.values()){
            if (gameModel.getPlayers().get(0).getEntrance().count(color)>0){
                c = color;
                break;
            }
        }

        try {
            gameModel.moveStudentToIsland(c, gameModel.getPlayers().get(0).getId(), islandIds.get(0));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assertEquals(6, gameModel.getPlayers().get(0).getEntrance().count());
        assertEquals(1, gameModel.getIslands().get(islandIds.get(0)).size());


    }

    /**
     * this method verifies that the getDeckSize method works correctly
     */
    @Test
    public void getDeckSize(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        try {
            assertEquals(10, gameModel.getDeckSize(gameModel.getPlayerIds().get(0)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            gameModel.playAssistantCard(gameModel.getPlayerIds().get(0), 5);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            assertEquals(9, gameModel.getDeckSize(gameModel.getPlayerIds().get(0)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    /**
     * this method verifies that the getDeck method works correctly
     */
    @Test
    public void getDeck(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        ArrayList<Integer> deck = gameModel.getPlayers().get(0).getDeck();

        try {
            assertEquals(deck, gameModel.getDeck(gameModel.getPlayerIds().get(0)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            gameModel.playAssistantCard(gameModel.getPlayerIds().get(0), 5);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        deck.remove(4);

        try {
            assertEquals(deck, gameModel.getDeck(gameModel.getPlayerIds().get(0)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    /**
     * this method verifies that the fillAllClouds method works correctly with two players
     */
    @Test
    public void fillAllClouds_TwoPlayers() {
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        assertEquals(106, gameModel.countStudentsInBag());

        LinkedHashMap<UUID, ArrayList<PawnColor>> map = gameModel.getClouds();

        for (UUID id : map.keySet()){
            assertEquals(0, map.get(id).size());
        }

        gameModel.fillAllClouds();

        map = gameModel.getClouds();

        for (UUID id : map.keySet()){
            assertEquals(3, map.get(id).size());
        }

        assertEquals(100, gameModel.countStudentsInBag());
    }

    /**
     * this method verifies that the fillAllClouds method works correctly with three players
     */
    @Test
    public void fillAllClouds_ThreePlayers() {
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo", "Carlo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        assertEquals(93, gameModel.countStudentsInBag());

        LinkedHashMap<UUID, ArrayList<PawnColor>> map = gameModel.getClouds();

        for (UUID id : map.keySet()){
            assertEquals(0, map.get(id).size());
        }

        gameModel.fillAllClouds();

        map = gameModel.getClouds();

        for (UUID id : map.keySet()){
            assertEquals(4, map.get(id).size());
        }

        assertEquals(81, gameModel.countStudentsInBag());
    }

    /**
     * this method verifies that the countIslands method works correctly
     */
    @Test
    public void countIslands() {
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        assertEquals(12, gameModel.countIslands());
    }




    /**
     * this method verifies that the clearPlayedAssistantCards method works correctly
     */
    @Test
    public void clearPlayedAssistantCards(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        assertEquals(0, gameModel.getPlayedAssistantCards().size());

        try {
            gameModel.playAssistantCard(gameModel.getPlayerIds().get(0), 5);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assertEquals(1, gameModel.getPlayedAssistantCards().size());

        gameModel.clearPlayedAssistantCards();

        assertEquals(0, gameModel.getPlayedAssistantCards().size());

    }

    /**
     * this method verifies that the exception is thrown when the steps entered are incorrect
     */
    @Test (expected = IllegalModelMoveException.class)
    public void moveMotherNature_exception(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        try {
            gameModel.playAssistantCard(gameModel.getPlayerIds().get(0), 6);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            gameModel.moveMotherNature(6, gameModel.getPlayerIds().get(0));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    /**
     * this method verifies that the moveMotherNature method works correctly
     */
    @Test
    public void moveMotherNature(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        try {
            gameModel.playAssistantCard(gameModel.getPlayerIds().get(0), 6);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            gameModel.moveMotherNature(3, gameModel.getPlayerIds().get(0));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    /**
     * this method verifies that printing is working correctly
     */
    @Test
    public void print(){
        List<String> nicknames = Arrays.asList("Alberto", "Bernardo");

        GameModel gameModel = new GameModel(false, nicknames, modelEventManager);

        String s = gameModel.toString();
        assertEquals(s, gameModel.toString());
    }


}