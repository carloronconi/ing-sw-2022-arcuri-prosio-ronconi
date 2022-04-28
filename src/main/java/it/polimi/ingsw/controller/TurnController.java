package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.CliView;
import it.polimi.ingsw.view.ViewInGameEvent;

import java.io.InvalidObjectException;
import java.util.*;

public class TurnController implements EventListener<ViewInGameEvent> {
    private List<UUID> playerIds;
    private final CliView view;
    private final GameModel gameModel;

    public TurnController(List<UUID> playerIds, CliView view, GameModel gameModel) {
        this.playerIds = playerIds;
        Collections.shuffle(playerIds);
        this.view = view;
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
    public void update(ViewInGameEvent viewInGameEvent, Object data) throws InvalidObjectException {

    }
}
