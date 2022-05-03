package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.util.*;

public class TurnController implements EventListener<GameViewEvent> {
    //private List<UUID> planningPlayerOrder;
    //private List<UUID> actionPlayerOrder;
    private List<UUID> playerOrder;
    private final GameModel gameModel;
    private final HashMap<UUID, VirtualView> viewMap;
    private int lastPlayedAssistant;
    private UUID lastPlayedCharacter;
    private PawnColor lastChosenStudent;
    private UUID lastChosenIsland;
    private int lastMotherNatureSteps;
    private UUID lastChosenCloud;

    private final GameMode gameMode;

    //TODO: delete if not necessary
    public TurnController(HashMap<UUID, VirtualView> viewMap, GameModel gameModel, GameMode gameMode) {
        this.gameModel = gameModel;
        this.viewMap = viewMap;
        this.gameMode = gameMode;
        ArrayList<UUID> list = new ArrayList<>(viewMap.keySet());
        /*planningPlayerOrder = list;
        actionPlayerOrder = list;*/
        playerOrder = list;
        Collections.shuffle(list);

        /*
        Random random = new Random();
        int index = random.nextInt(planningPlayerOrder.size());
        UUID firstPlayer = planningPlayerOrder.get(index);
        reorderPlanning(firstPlayer);
        */
    }


    /*
    private void reorderPlanning(UUID firstPlayer){
        int first = planningPlayerOrder.indexOf(firstPlayer);
        ArrayList<UUID> list = new ArrayList<>();
        list.add(firstPlayer);
        for (int i = first; i < planningPlayerOrder.size(); i++) {
            list.add(planningPlayerOrder.get(i));
        }
        for (int i = 0; i < first; i++) {
            list.add(planningPlayerOrder.get(i));
        }
    }
    */

    private void reorderPlayerOrder(HashMap<UUID, Integer> map){
        List<UUID> tempPlayerOrder = new ArrayList<>();
        HashMap<UUID, Integer> tempMap = new HashMap<>(map);
        for (int i = 0; i < map.size(); i++) {
            int minValue = 11;
            UUID minPlayer = null;
            for (UUID player : tempMap.keySet()){
                if (tempMap.get(player)<minValue) {
                    minValue = tempMap.get(player);
                    minPlayer = player;
                }
            }
            tempPlayerOrder.add(minPlayer);
            tempMap.remove(minPlayer);
        }
        playerOrder = tempPlayerOrder;

    }

    public boolean startRound() {
        startPlanningPhase();
        return startActionPhase();
    }

    private void startPlanningPhase() {
        gameModel.fillAllClouds();
        gameModel.clearPlayedAssistantCards();


        for (UUID player : playerOrder){
            viewMap.get(player).getAssistantCard();
            while (true){
                try {
                    gameModel.playAssistantCard(player,lastPlayedAssistant);
                    break;
                } catch (NoSuchFieldException e) {
                    viewMap.get(player).invalidAssistantCard();
                }
            }
        }

        HashMap<UUID, Integer> map = gameModel.getPlayedAssistantCards();
        reorderPlayerOrder(map);
    }

    private boolean isGameOver(UUID player){
        try {
            if (gameModel.getNumOfTowers(player) == 0 || gameModel.countIslands() == 3 ||
                    gameModel.countStudentsInBag() == 0 || gameModel.getDeckSize(player) == 0) return true;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean startActionPhase(){
        for (UUID player : playerOrder){
            VirtualView view = viewMap.get(player);
            view.playerTurn();
            if (gameMode == GameMode.HARD){
                view.chooseCharacter();
                if (lastPlayedCharacter != null){
                    while (true){
                        try {
                            gameModel.payAndGetCharacter(player, lastPlayedCharacter);
                            break;
                        } catch (NoSuchFieldException e) {
                            view.invalidCharacterChoice();
                        }
                    }
                }
            }
            int times = (playerOrder.size() == 2)? 3: 4;
            for (int i = 0; i < times; i++) {
                view.moveStudent();
                if (lastChosenIsland == null){
                    try {
                        gameModel.moveStudentToDining(lastChosenStudent, player);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        gameModel.moveStudentToIsland(lastChosenStudent, player, lastChosenIsland);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            }

            view.moveMotherNature();
            while (true){
                try {
                    gameModel.moveMotherNature(lastMotherNatureSteps, player);
                    break;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e){
                    view.invalidMNMove();
                }
            }

            if (isGameOver(player)) return true;

            view.chooseCloud();
            try {
                gameModel.moveCloudToEntrance(lastChosenCloud, player);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }
        return false;
    }


    @Override
    public void update(GameViewEvent modelEvent)  {
        if (modelEvent instanceof SetAssistantCard){
            lastPlayedAssistant = ((SetAssistantCard) modelEvent).getCard();
        } else if (modelEvent instanceof  ChosenCharacter){
            lastPlayedCharacter = ((ChosenCharacter) modelEvent).getChosenCharacter();
        } else if (modelEvent instanceof MovedStudent){
            lastChosenStudent = ((MovedStudent) modelEvent).getColor();
            lastChosenIsland = ((MovedStudent) modelEvent).getIslandId();
        } else if (modelEvent instanceof MovedMotherNature){
            lastMotherNatureSteps = ((MovedMotherNature) modelEvent).getMotherNatureSteps();
        } else if (modelEvent instanceof  ChosenCloud){
            lastChosenCloud = ((ChosenCloud) modelEvent).getCloud();
        }
    }
}
