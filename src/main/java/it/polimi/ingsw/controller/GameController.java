package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.controllercalls.GetNickname;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.model.GameModel;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * controller listens to view events according to the MVC pattern
 */
public class GameController implements EventListener<SetupViewEvent> {
    private GameMode gameMode;
    private ControllerState controllerState;
    private int numOfPlayers;
    private GameModel gameModel = null;
    private final List<String> playerNicknames;
    private final VirtualView firstVirtualView;
    private ArrayList<VirtualView> virtualViews;
    private final EventManager<ModelEvent> modelEventEventManager;
    private TurnController turnController;

    public GameController(EventManager<ModelEvent> modelEventEventManager) {
        playerNicknames = new ArrayList<>();
        firstVirtualView = new VirtualView();
        controllerState = ControllerState.INITIAL_SETUP;
        this.modelEventEventManager = modelEventEventManager;
    }

    /**
     * call to start the game
     */
    public void startGame(){
        firstVirtualView.getPreferences();

        virtualViews = new ArrayList<>();
        virtualViews.add(firstVirtualView);

        for (int i = 0; i <numOfPlayers-1 ; i++) {
            virtualViews.add(new VirtualView());
        }

        for (VirtualView virtualView : virtualViews){
            virtualView.getNickname();
            ArrayList<String> nickList = new ArrayList<>(playerNicknames);
            nickList.remove(nickList.size()-1);
            if (nickList.contains(playerNicknames.get(playerNicknames.size()-1))){
                playerNicknames.remove(playerNicknames.size()-1);
                virtualView.invalidNickname();
            }
        }

        if (playerNicknames.size()==numOfPlayers){
            boolean expertMode = (gameMode == GameMode.HARD);
            gameModel = new GameModel(expertMode, playerNicknames, modelEventEventManager);
        }

        ArrayList<UUID> playerIds = gameModel.getPlayerIds();
        turnController = new TurnController(playerIds, gameModel);
        controllerState = ControllerState.PLAYING_GAME;

    }


    @Override
    public void update(SetupViewEvent viewEvent) {
        if (viewEvent instanceof Handshake){
            firstVirtualView.sendAcknowledgement();
        }else if (viewEvent instanceof SetPreferences){
            numOfPlayers = ((SetPreferences) viewEvent).getNumOfPlayers();
            gameMode = ((SetPreferences) viewEvent).getGameMode();
        } else if (viewEvent instanceof SetNickname){
            String nickname = ((SetNickname) viewEvent).getNickname();
            playerNicknames.add(nickname);

        }
    }
}
