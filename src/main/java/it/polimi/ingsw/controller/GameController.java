package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.model.GameModel;

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
    private final HashMap<Integer, String> mapOfPlayerNicknames = new HashMap<>();
    private final List<VirtualView> virtualViews;
    private UUID id; //virtual view id

    private final EventManager<ModelEvent> modelEventEventManager;
    private TurnController turnController;
    private boolean playAgain = false;

    private final Object lock = new Object();
    private int numOfNicknamesAdded =0;

    public GameController(EventManager<ModelEvent> modelEventEventManager) {
        playerNicknames = new ArrayList<>();
        controllerState = ControllerState.INITIAL_SETUP;
        this.modelEventEventManager = modelEventEventManager;
        virtualViews = new ArrayList<>();
    }

    public List<VirtualView> getVirtualViews(){
        return virtualViews;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public List<String> getPlayerNicknames() {
        return new ArrayList<>(playerNicknames);
    }

    public boolean isAssistantCardIllegal(int card, int virtualViewInstanceNum) {
        UUID playerId = virtualViewInstanceToId(virtualViewInstanceNum);
        try {
            return gameModel.isAssistantCardIllegal(playerId, card);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public boolean isCharacterCardIllegal(AvailableCharacter card, int virtualViewInstanceNum){
        UUID playerId = virtualViewInstanceToId(virtualViewInstanceNum);
        try {
            return gameModel.isCharacterCardIllegal(playerId, card);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isMNMoveIllegal(int steps, int virtualViewInstanceNum){
        UUID playerId = virtualViewInstanceToId(virtualViewInstanceNum);
        try {
            return gameModel.isMNMoveIllegal(steps, playerId);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isStudentMoveIllegal(PawnColor color, int virtualViewInstanceNum){
        UUID playerId = virtualViewInstanceToId(virtualViewInstanceNum);
        int available = gameModel.getEntrances().get(playerId).get(color);
        return available <= 0;
    }

    private UUID virtualViewInstanceToId(int virtualViewInstanceNum){
        ArrayList<UUID> playerIds = gameModel.getPlayerIds();
        UUID playerId = playerIds.get(virtualViewInstanceNum);
        return playerId;
    }

    public GameMode getGameMode() {
        return gameMode;
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
            if(((SetNickname) viewEvent).getVirtualView().getThisInstanceNumber()>0){
                synchronized (lock){
                    numOfNicknamesAdded++;
                    lock.notifyAll();
                }
            }else numOfNicknamesAdded++;
            String nickname = ((SetNickname) viewEvent).getNickname();
            //playerNicknames.add(nickname);
            //System.out.println(playerNicknames);
            VirtualView virtualView = ((SetNickname) viewEvent).getVirtualView();
            mapOfPlayerNicknames.put(virtualView.getThisInstanceNumber(), nickname);
            System.out.println(mapOfPlayerNicknames);
            synchronized (virtualViews){
                virtualViews.add(virtualView);
            }

        } else if (viewEvent instanceof SetPreferences) {
            numOfPlayers = ((SetPreferences) viewEvent).getNumOfPlayers();
            gameMode = ((SetPreferences) viewEvent).getGameMode();
            synchronized (lock) {
                while (numOfNicknamesAdded < numOfPlayers) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (numOfNicknamesAdded == numOfPlayers) {
                boolean expertMode = (gameMode == GameMode.HARD);
                synchronized (virtualViews){
                    Iterator<VirtualView> iterator = virtualViews.iterator();
                    while(iterator.hasNext()){
                        VirtualView view = iterator.next();
                        modelEventEventManager.subscribe(view);
                    }
                }
                /*
                for (VirtualView v : virtualViews) {
                    modelEventEventManager.subscribe(v);

                }*/
                for (int i = 0; i<numOfPlayers; i++){
                    playerNicknames.add(mapOfPlayerNicknames.get(i));
                }
                System.out.println("initializing game model with the following nicknames: " + playerNicknames);
                gameModel = new GameModel(expertMode, playerNicknames, modelEventEventManager);
                controllerState = ControllerState.PLAYING_GAME;
                turnController = new TurnController(gameModel, gameMode);
                synchronized (virtualViews){
                    for (VirtualView v : virtualViews) {

                        v.subscribeToEventManager(turnController);
                        v.id = turnController.getPlayerId(v.getThisInstanceNumber());


                    }
                }
                turnController.firstOrderShuffle();

                gameModel.fillAllClouds();
                gameModel.clearPlayedAssistantCards();
            } else if(viewEvent instanceof ReadyToPlay){

            }
        } else if (viewEvent instanceof SetPlayAgain) {
            playAgain = ((SetPlayAgain) viewEvent).isPlayAgain();
        }
    }
}
