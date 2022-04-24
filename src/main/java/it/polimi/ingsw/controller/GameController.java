package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventType;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.CliView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController implements EventListener {
    private final CliView view;
    private GameMode gameMode;
    private int numOfPlayers;
    //private final GameModel gameModel;
    private final List<String> playerNicknames;

    public GameController(CliView view) {
        this.view = view;
        //this.gameModel = gameModel;
        playerNicknames = new ArrayList<>();
    }

    public void startSetup(){

        List<String> choices = new ArrayList<>();
        for (GameMode mode : GameMode.values()) choices.add(mode.name());
        view.showMultipleChoicePrompt(choices, "Choose game mode", EventType.CHOSE_GAME_MODE);
        choices = Arrays.asList("2", "3");
        view.showMultipleChoicePrompt(choices, "Choose number of players", EventType.CHOSE_NUM_OF_PLAYERS);

        for (int i = 0; i < numOfPlayers; i++) {
            view.showTextInputPrompt("Choose nickname for player number " + i, EventType.CHOSE_NICKNAME);
        }

        choices = Arrays.asList("YES", "NO");
        view.showMultipleChoicePrompt(choices, "Ready to start the game", EventType.STARTED_GAME);

        //TODO: initialise gameModel accordingly
    }

    @Override
    public void update(EventType eventType, String data) {
        switch (eventType) {
            case CHOSE_GAME_MODE: gameMode = GameMode.valueOf(data);
                break;
            case CHOSE_NUM_OF_PLAYERS: numOfPlayers = Integer.parseInt(data);
                break;
            case CHOSE_NICKNAME: playerNicknames.add(data);
                break;
            case STARTED_GAME:
                break;
        }
    }
}
