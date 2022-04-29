package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.controllercalls.GetNickname;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.Handshake;
import it.polimi.ingsw.networkmessages.viewevents.SetNickname;
import it.polimi.ingsw.networkmessages.viewevents.SetPreferences;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.model.GameModel;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * controller listens to view events according to the MVC pattern
 */
public class GameController implements EventListener<ViewEvent> {
    private GameMode gameMode;
    private ControllerState controllerState;
    private int numOfPlayers;
    private GameModel gameModel = null;
    private final List<String> playerNicknames;
    private final VirtualView virtualView;
    private final EventManager<ModelEvent> modelEventEventManager;

    public GameController(VirtualView virtualView, EventManager<ModelEvent> modelEventEventManager) {
        playerNicknames = new ArrayList<>();
        controllerState = ControllerState.INITIAL_SETUP;
        this.virtualView = virtualView;
        this.modelEventEventManager = modelEventEventManager;
    }

    /**
     * call to start the game
     */
    public void startGame(){


    }


    @Override
    public void update(ViewEvent viewEvent) throws InvalidObjectException {
        if (viewEvent instanceof Handshake){
            virtualView.sendAcknowledgement();
        }else if (viewEvent instanceof SetPreferences){
            numOfPlayers = ((SetPreferences) viewEvent).getNumOfPlayers();
            gameMode = ((SetPreferences) viewEvent).getGameMode();
        } else if (viewEvent instanceof SetNickname){
            String nickname = ((SetNickname) viewEvent).getNickname();
            if (playerNicknames.contains(nickname)) virtualView.invalidNickname();
            else playerNicknames.add(nickname);
            if (playerNicknames.size()==numOfPlayers){
                boolean expertMode = (gameMode == GameMode.HARD);
                gameModel = new GameModel(expertMode, playerNicknames, modelEventEventManager);
            }
        }
    }
}
