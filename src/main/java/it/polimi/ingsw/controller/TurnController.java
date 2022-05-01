package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.util.*;

public class TurnController implements EventListener<GameViewEvent> {
    private List<UUID> planningPlayerOrder;
    private List<UUID> actionPlayerOrder;
    private final GameModel gameModel;
    private final HashMap<UUID, VirtualView> viewMap;
    private int lastPlayedAssistant;

    public TurnController(HashMap<UUID, VirtualView> viewMap, GameModel gameModel) {
        this.gameModel = gameModel;
        this.viewMap = viewMap;
        ArrayList<UUID> list = new ArrayList<>(viewMap.keySet());
        planningPlayerOrder = list;
        actionPlayerOrder = list;

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


        for (UUID player : viewMap.keySet()){
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
        //TODO: reorder the map according to the value of the played cards (smallest to largest)
        //update planing player order
        //update action player order
    }

    private void startActionPhase(){

    }


    @Override
    public void update(GameViewEvent modelEvent)  {
        if (modelEvent instanceof SetAssistantCard){
            lastPlayedAssistant = ((SetAssistantCard) modelEvent).getCard();
        }
    }
}
