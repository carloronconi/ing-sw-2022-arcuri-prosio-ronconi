package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.model.charactercards.Character;
import it.polimi.ingsw.model.charactercards.SwapperCharacter;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithColor;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithIsland;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithPlayer;
//import it.polimi.ingsw.networkmessages.controllercalls.LetsPlay;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.util.*;

public class TurnController implements EventListener<ViewEvent> {
    private ArrayList<UUID> playerOrder; //reorder it only after planning phase
    private int currentPlayerIndex; //index of the player who is currently playing in playerOrder
    private TurnState phase = TurnState.PLANNING;
    private final GameModel gameModel;
    private final GameMode gameMode;


    public TurnController(GameModel gameModel, GameMode gameMode) {
        this.gameModel = gameModel;
        this.gameMode = gameMode;

        playerOrder = new ArrayList<>(gameModel.getPlayerIds());
        Collections.shuffle(playerOrder);
        currentPlayerIndex = 0;
    }

    public UUID getPlayerId(int index){
        return playerOrder.get(index);
    }

    public UUID getCurrentPlayer(){
        return playerOrder.get(currentPlayerIndex);
    }

    public TurnState getCurrentPhase(){
        return phase;
    }

    /**
     * method to be called when a player has finished doing all the moves in its turn
     */
    public void playerFinishedTurn(){
        //three cases:
        //1. we are in the middle of either phase, so we just need to increment the currentPlayerIndex
        if(currentPlayerIndex < playerOrder.size()-1){
            currentPlayerIndex++;
        } else { //we are at the end of one of the two possible phases
            //2. we are at the end of ACTION phase, so we just roll back the currentPlayerIndex to 0 and
            //   update the phase and turn it into PLANNING
            if (phase == TurnState.ACTION){
                currentPlayerIndex = 0;
                phase = TurnState.PLANNING;
            //3. we are at the end og PLANNING phase, so we need to change the ordering of playerOrder list,
            //   roll back the currentPlayerIndex to 0 and update the phase and turn it into ACTION
            } else {
                reorderPlayerOrder();
                currentPlayerIndex = 0;
                phase = TurnState.ACTION;
            }
        }
    }


    private void reorderPlayerOrder(){

        HashMap<UUID, Integer> map = gameModel.getPlayedAssistantCards();
        ArrayList<UUID> tempPlayerOrder = new ArrayList<>();
        HashMap<UUID, Integer> tempMap = new HashMap<>(map);
        for (int i = 0; i < map.size(); i++) {
            int minValue = 11;
            UUID minPlayer = null;
            for (UUID player : tempMap.keySet()){
                if (tempMap.get(player)<minValue) {
                    minValue = tempMap.get(player);
                    minPlayer = player;
                }
            }
            tempPlayerOrder.add(minPlayer);
            tempMap.remove(minPlayer);
        }
        playerOrder = tempPlayerOrder;

    }



    /*
    public boolean startRound() {
        startPlanningPhase();
        return startActionPhase();
    }

    private void startPlanningPhase() {
        gameModel.fillAllClouds();
        gameModel.clearPlayedAssistantCards();


        for (UUID player : playerOrder){
            viewMap.get(player).getAssistantCard();
            while (true){
                try {
                    gameModel.playAssistantCard(player,lastPlayedAssistant);
                    break;
                } catch (NoSuchFieldException e) {
                    viewMap.get(player).invalidAssistantCard();
                }
            }
        }

        HashMap<UUID, Integer> map = gameModel.getPlayedAssistantCards();
        reorderPlayerOrder(map);
    }
     */

    private boolean isGameOver(UUID player){
        try {
            if (gameModel.getNumOfTowers(player) == 0 || gameModel.countIslands() == 3 ||
                    gameModel.countStudentsInBag() == 0 || gameModel.getDeckSize(player) == 0) return true;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }
/*
    private boolean startActionPhase(){
        for (UUID player : playerOrder){
            VirtualView view = viewMap.get(player);
            view.playerTurn();
            if (gameMode == GameMode.HARD){
                view.chooseCharacter();

                if (lastPlayedCharacter != null){
                    while (true){
                        try {
                            Character character = gameModel.payAndGetCharacter(player, lastPlayedCharacter);
                            if (character instanceof SwapperCharacter){
                                view.getColorSwap();
                                ((SwapperCharacter) character).setupColorSwaps(lastGivenSwap, lastTakenSwap);
                            }
                            if (character instanceof EffectWithColor){
                                view.getColorChoice();
                                ((EffectWithColor) character).setEffectColor(lastChosenColor);
                            }
                            if (character instanceof EffectWithIsland){
                                view.getIslandChoice();
                                ((EffectWithIsland) character).setEffectIsland(lastEffectChosenIsland);
                            }
                            if (character instanceof EffectWithPlayer){
                                ((EffectWithPlayer) character).setEffectPlayer(player);
                            }
                            character.useEffect();
                            break;
                        } catch (NoSuchFieldException e) {
                            view.invalidCharacterChoice();
                        }
                    }
                }

            }
            int times = (playerOrder.size() == 2)? 3: 4;
            for (int i = 0; i < times; i++) {
                view.moveStudent();
                if (lastChosenIsland == null){
                    try {
                        gameModel.moveStudentToDining(lastChosenStudent, player);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        gameModel.moveStudentToIsland(lastChosenStudent, player, lastChosenIsland);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            }

            view.moveMotherNature();
            while (true){
                try {
                    gameModel.moveMotherNature(lastMotherNatureSteps, player);
                    break;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e){
                    view.invalidMNMove();
                }
            }

            if (isGameOver(player)) return true;

            view.chooseCloud();
            try {
                gameModel.moveCloudToEntrance(lastChosenCloud, player);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
*/

    @Override
    public void update(ViewEvent modelEvent)  {

        if (modelEvent instanceof SetAssistantCard){
            int card = ((SetAssistantCard) modelEvent).getCard();
            UUID player = getCurrentPlayer();
            try {
                gameModel.playAssistantCard(player, card);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            //lastPlayedAssistant = ((SetAssistantCard) modelEvent).getCard();
        } else if (modelEvent instanceof  ChosenCharacter){
            //lastPlayedCharacter = ((ChosenCharacter) modelEvent).getChosenCharacter();
        } else if (modelEvent instanceof MovedStudent){
            //lastChosenStudent = ((MovedStudent) modelEvent).getColor();
            //lastChosenIsland = ((MovedStudent) modelEvent).getIslandId();
        } else if (modelEvent instanceof MovedMotherNature){
            //lastMotherNatureSteps = ((MovedMotherNature) modelEvent).getMotherNatureSteps();
        } else if (modelEvent instanceof  ChosenCloud){
            //lastChosenCloud = ((ChosenCloud) modelEvent).getCloud();
        } else if (modelEvent instanceof SetColorSwap){
            //lastGivenSwap = ((SetColorSwap) modelEvent).getGive();
            //lastTakenSwap = ((SetColorSwap) modelEvent).getTake();
        } else if (modelEvent instanceof SetColorChoice){
            //lastChosenColor = ((SetColorChoice) modelEvent).getColor();
        } else if (modelEvent instanceof SetIslandChoice){
            //lastEffectChosenIsland = ((SetIslandChoice) modelEvent).getIsland();
        }
    }
}
