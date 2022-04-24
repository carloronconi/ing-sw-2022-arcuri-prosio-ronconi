package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.ControllerEventType;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.CliView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController implements EventListener<ControllerEventType> {
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
            view.showMultipleChoicePrompt(choices, "Choose game mode", ControllerEventType.CHOSE_GAME_MODE);
            choices = Arrays.asList("2", "3");
            view.showMultipleChoicePrompt(choices, "Choose number of players", ControllerEventType.CHOSE_NUM_OF_PLAYERS);

            for (int i = 0; i < numOfPlayers; i++) {
                view.showTextInputPrompt("Choose nickname for player number " + i, ControllerEventType.CHOSE_NICKNAME);
            }

            choices = Arrays.asList("YES", "NO");
            view.showMultipleChoicePrompt(choices, "Ready to start the game? If not, you will start over with setup", ControllerEventType.STARTED_GAME);
        }

        boolean expertMode = (gameMode == GameMode.HARD);
        gameModel = new GameModel(expertMode, playerNicknames);
    }

    @Override
    public void update(ControllerEventType controllerEventType, String data) {
        switch (controllerEventType) {
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
