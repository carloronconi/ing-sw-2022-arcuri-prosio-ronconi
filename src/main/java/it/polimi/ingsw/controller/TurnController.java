package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.GameViewEvent;

import java.io.InvalidObjectException;
import java.util.*;

public class TurnController implements EventListener<GameViewEvent> {
    private List<UUID> planningPlayerOrder;
    private List<UUID> actionPlayerOrder;
    private final GameModel gameModel;

    public TurnController(List<UUID> playerIds, GameModel gameModel) {
        this.gameModel = gameModel;
        planningPlayerOrder = playerIds;
        actionPlayerOrder = playerIds;

        Random random = new Random();
        int index = random.nextInt(planningPlayerOrder.size());
        UUID firstPlayer = planningPlayerOrder.get(index);
        reorderPlanning(firstPlayer);

    }

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

    public void startRound() {
        startPlanningPhase();
        startActionPhase();
    }

    private void startPlanningPhase() {
        gameModel.fillAllClouds();
        gameModel.clearPlayedAssistantCards();

        for (UUID player : playerIds){
            ArrayList<Integer> choicesInt = gameModel.getDeck(player);
            ArrayList<String> choices = new ArrayList<>();
            for (Integer i : choicesInt){
                choices.add(i.toString());
            }
            //view.showMultipleChoicePrompt(choices, "Choose which assistant card you want to play", );
            //play assistant card
        }
    }

    private void startActionPhase(){

    }


    @Override
    public void update(GameViewEvent modelEvent)  {

    }
}
