package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.model.ModelEventType;
import it.polimi.ingsw.view.ViewEventType;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.CliView;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * controller listens to view events according to the MVC pattern
 */
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

    /**
     * call to start the game
     * @param modelEventManager with subscribers already initialized
     */
    public void startGame(EventManager<ModelEventType> modelEventManager){

        while (controllerState == ControllerState.INITIAL_SETUP){
            playerNicknames.clear();
            List<String> choices = new ArrayList<>();
            for (GameMode mode : GameMode.values()) choices.add(mode.name());
            view.showMultipleChoicePrompt(choices, "Choose game mode", ViewEventType.CHOSE_GAME_MODE);
            choices = Arrays.asList("2", "3");
            view.showMultipleChoicePrompt(choices, "Choose number of players", ViewEventType.CHOSE_NUM_OF_PLAYERS);

            for (int i = 0; i < numOfPlayers; i++) {
                int j = i+1;
                view.showTextInputPrompt("Choose nickname for player number " + j, ViewEventType.CHOSE_NICKNAME, s-> s.replaceAll("\\s",""));
            }

            choices = Arrays.asList("YES", "NO");
            view.showMultipleChoicePrompt(choices, "Ready to start the game? If not, you will start over with setup", ViewEventType.STARTED_GAME);
        }

        boolean expertMode = (gameMode == GameMode.HARD);
        gameModel = new GameModel(expertMode, playerNicknames, modelEventManager);
    }

    /**
     * method to react to all the view events
     * @param viewEventType specific view event that the controller has to react to
     * @param data relative to the event
     * @throws InvalidObjectException if data is invalid
     */
    @Override
    public void update(ViewEventType viewEventType, String data) throws InvalidObjectException {
        switch (viewEventType) {
            case CHOSE_GAME_MODE: gameMode = GameMode.valueOf(data);
                break;
            case CHOSE_NUM_OF_PLAYERS: numOfPlayers = Integer.parseInt(data);
                break;
            case CHOSE_NICKNAME:
                if (playerNicknames.contains(data)) throw new InvalidObjectException("Nickname already used by other player");
                playerNicknames.add(data);
                break;
            case STARTED_GAME: if (data.equals("YES")) controllerState = ControllerState.PLAYING_GAME;
                break;
        }
    }


}
