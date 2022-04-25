package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.view.ViewEventType;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.CliView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController implements EventListener<ViewEventType> {
    private final CliView view;
    private GameMode gameMode;
    private ControllerState controllerState;
    private int numOfPlayers;
    private GameModel gameModel = null;
    private final List<String> playerNicknames;


    public GameController(CliView view) {
        this.view = view;
        playerNicknames = new ArrayList<>();
        controllerState = ControllerState.INITIAL_SETUP;
    }

    public void startSetup(){

        while (controllerState == ControllerState.INITIAL_SETUP){
            List<String> choices = new ArrayList<>();
            for (GameMode mode : GameMode.values()) choices.add(mode.name());
            view.showMultipleChoicePrompt(choices, "Choose game mode", ViewEventType.CHOSE_GAME_MODE);
            choices = Arrays.asList("2", "3");
            view.showMultipleChoicePrompt(choices, "Choose number of players", ViewEventType.CHOSE_NUM_OF_PLAYERS);

            for (int i = 0; i < numOfPlayers; i++) {
                view.showTextInputPrompt("Choose nickname for player number " + i, ViewEventType.CHOSE_NICKNAME);
            }

            choices = Arrays.asList("YES", "NO");
            view.showMultipleChoicePrompt(choices, "Ready to start the game? If not, you will start over with setup", ViewEventType.STARTED_GAME);
        }

        boolean expertMode = (gameMode == GameMode.HARD);
        gameModel = new GameModel(expertMode, playerNicknames);
    }

    @Override
    public void update(ViewEventType viewEventType, String data) {
        switch (viewEventType) {
            case CHOSE_GAME_MODE: gameMode = GameMode.valueOf(data);
                break;
            case CHOSE_NUM_OF_PLAYERS: numOfPlayers = Integer.parseInt(data);
                break;
            case CHOSE_NICKNAME: playerNicknames.add(data);
                break;
            case STARTED_GAME: if (data.equals("YES")) controllerState = ControllerState.PLAYING_GAME;
                break;
        }
    }


}
