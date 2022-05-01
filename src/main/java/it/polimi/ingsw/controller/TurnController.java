package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.GameViewEvent;

import java.io.InvalidObjectException;
import java.util.*;

public class TurnController implements EventListener<GameViewEvent> {
    private List<UUID> playerIds;
    private final GameModel gameModel;

    public TurnController(List<UUID> playerIds, GameModel gameModel) {
        this.playerIds = playerIds;
        Collections.shuffle(playerIds);
        this.gameModel = gameModel;
    }

    public void startRound() throws NoSuchFieldException {
        startPlanningPhase();
        startActionPhase();
    }

    private void startPlanningPhase() throws NoSuchFieldException {
        gameModel.fillAllClouds();
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
