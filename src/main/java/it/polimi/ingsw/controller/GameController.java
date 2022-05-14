package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.controllercalls.GetNickname;
import it.polimi.ingsw.networkmessages.controllercalls.InvalidNickname;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.model.GameModel;

import java.io.InvalidObjectException;
import java.util.*;

/**
 * controller listens to view events according to the MVC pattern
 */
public class GameController implements EventListener<ViewEvent> {
    private GameMode gameMode;
    private ControllerState controllerState;
    private int numOfPlayers;
    private GameModel gameModel = null;
    private final List<String> playerNicknames;
    private List<VirtualView> virtualViews;

    private final EventManager<ModelEvent> modelEventEventManager;
    private TurnController turnController;
    private boolean playAgain = false;

    public GameController(EventManager<ModelEvent> modelEventEventManager) {
        playerNicknames = new ArrayList<>();
        controllerState = ControllerState.INITIAL_SETUP;
        this.modelEventEventManager = modelEventEventManager;
        virtualViews = new ArrayList<>();
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public List<String> getPlayerNicknames() {
        return new ArrayList<>(playerNicknames);
    }

    public boolean isAssistantCardIllegal(int card, int virtualViewInstanceNum) {
        ArrayList<UUID> playerIds = gameModel.getPlayerIds();
        UUID playerId = playerIds.get(virtualViewInstanceNum);
        return false;
    }

    /**
     * call to start the game
     */
    /*
    public void startGame(){


        boolean gameIsOver = false;
        while (!gameIsOver){
            gameIsOver = turnController.startRound();
        }
        controllerState = ControllerState.GAME_OVER;

        for (VirtualView view: virtualViews){
            view.gameOver();
            if (view == firstVirtualView) view.askPlayAgain();
        }

        if (playAgain){
            controllerState = ControllerState.INITIAL_SETUP;
            startGame();
        }

    }
    */
    @Override
    public void update(ViewEvent viewEvent) {
        if (viewEvent instanceof SetNickname) {
            String nickname = ((SetNickname) viewEvent).getNickname();
            playerNicknames.add(nickname);
            VirtualView virtualView = ((SetNickname) viewEvent).getVirtualView();
            virtualViews.add(virtualView);
            for (VirtualView v : virtualViews) {
                v.subscribeToEventManager(turnController);
            }

        } else if (viewEvent instanceof SetPreferences) {
            numOfPlayers = ((SetPreferences) viewEvent).getNumOfPlayers();
            gameMode = ((SetPreferences) viewEvent).getGameMode();
            if (playerNicknames.size() == numOfPlayers) {
                boolean expertMode = (gameMode == GameMode.HARD);
                for (VirtualView v : virtualViews) {
                    modelEventEventManager.subscribe(v);
                }
                gameModel = new GameModel(expertMode, playerNicknames, modelEventEventManager);
                controllerState = ControllerState.PLAYING_GAME;
                turnController = new TurnController(gameModel, gameMode);
            }
        } else if (viewEvent instanceof SetPlayAgain) {
                playAgain = ((SetPlayAgain) viewEvent).isPlayAgain();
            }
        }
}
