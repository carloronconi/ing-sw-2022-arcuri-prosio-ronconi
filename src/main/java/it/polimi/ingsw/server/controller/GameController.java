package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.utilities.EventListener;
import it.polimi.ingsw.utilities.EventManager;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;
import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.server.model.GameModel;

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
    private final LinkedHashMap<Integer, String> mapOfPlayerNicknames = new LinkedHashMap<>();
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
        return new ArrayList<>(/*playerNicknames*/mapOfPlayerNicknames.values());
    }

    /**
     * this method checks if the AssistantCard played by a player is correct
     * @param card played by the player corresponding to the virtualView, as an integer, passed in input
     * @param virtualViewInstanceNum integer corresponding to the virtualView in turn corresponding to a player
     * @return a Boolean value indicating whether the chosen card is illegal or not
     */
    public boolean isAssistantCardIllegal(int card, int virtualViewInstanceNum) {
        UUID playerId = virtualViewInstanceToId(virtualViewInstanceNum);
        try {
            return gameModel.isAssistantCardIllegal(playerId, card);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * this method checks if the CharacterCard played by a player is correct
     * @param card played by the player corresponding to the virtualView, as an integer, passed in input
     * @param virtualViewInstanceNum integer corresponding to the virtualView in turn corresponding to a player
     * @return a Boolean value indicating whether the chosen card is illegal or not
     */
    public boolean isCharacterCardIllegal(AvailableCharacter card, int virtualViewInstanceNum){
        UUID playerId = virtualViewInstanceToId(virtualViewInstanceNum);
        try {
            return gameModel.isCharacterCardIllegal(playerId, card);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this method checks if the steps of motherNature chosen by a player are correct
     * @param steps number of steps a player has chosen for Mother Nature to take
     * @param virtualViewInstanceNum integer corresponding to the virtualView in turn corresponding to a player
     * @return a Boolean value indicating whether the chosen steps are illegal or not
     */
    public boolean isMNMoveIllegal(int steps, int virtualViewInstanceNum){
        UUID playerId = virtualViewInstanceToId(virtualViewInstanceNum);
        try {
            return gameModel.isMNMoveIllegal(steps, playerId);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this method checks whether the student moved by a player is illegal or not
     * @param color color of the student chosen for the move
     * @param virtualViewInstanceNum integer corresponding to the virtualView in turn corresponding to a player
     * @return returns a Boolean value indicating whether movement is allowed
     */
    public boolean isStudentMoveIllegal(PawnColor color, int virtualViewInstanceNum){
        UUID playerId = virtualViewInstanceToId(virtualViewInstanceNum);
        int available = gameModel.getEntrances().get(playerId).get(color);
        return available <= 0;
    }

    /**
     * this method converts the integer of the virtual view to the corresponding player
     * @param virtualViewInstanceNum integer corresponding to the virtualView in turn corresponding to a player
     * @return Player ID
     */
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

            if (numOfNicknamesAdded > numOfPlayers){
                virtualViews.get(0).gameOver(null);
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

                for (int i = 0; i<numOfPlayers; i++){
                    playerNicknames.add(mapOfPlayerNicknames.get(i));
                }
                System.out.println("initializing game model with the following nicknames: " + mapOfPlayerNicknames.values());
                ArrayList<String> orderedNicknames = new ArrayList<>(mapOfPlayerNicknames.size());
                for (int i = 0; i<mapOfPlayerNicknames.size(); i++){
                    orderedNicknames.add(mapOfPlayerNicknames.get(i));
                }
                System.out.println("orderedNicknames = " + orderedNicknames);
                gameModel = new GameModel(expertMode, orderedNicknames, modelEventEventManager);
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
            }
        } else if (viewEvent instanceof SetPlayAgain) {
            playAgain = ((SetPlayAgain) viewEvent).isPlayAgain();
        }
    }
}
