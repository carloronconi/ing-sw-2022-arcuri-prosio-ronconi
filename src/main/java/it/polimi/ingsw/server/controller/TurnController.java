package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.server.model.charactercards.Character;
import it.polimi.ingsw.server.model.charactercards.ColorSwap;
import it.polimi.ingsw.server.model.charactercards.SwapperCharacter;
import it.polimi.ingsw.server.model.charactercards.effectarguments.EffectWithColor;
import it.polimi.ingsw.server.model.charactercards.effectarguments.EffectWithIsland;
import it.polimi.ingsw.server.model.charactercards.effectarguments.EffectWithPlayer;
import it.polimi.ingsw.utilities.EventListener;
//import it.polimi.ingsw.networkmessages.controllercalls.LetsPlay;
import it.polimi.ingsw.networkmessages.viewevents.*;

import java.util.*;

public class TurnController implements EventListener<ViewEvent> {
    private ArrayList<UUID> playerOrder; //reorder it only after planning phase
    private int currentPlayerIndex; //index of the player who is currently playing in playerOrder
    private TurnState phase = TurnState.PLANNING;
    private final GameModel gameModel;
    private final GameMode gameMode;

    private Character lastCharacter;


    public TurnController(GameModel gameModel, GameMode gameMode) {
        this.gameModel = gameModel;
        this.gameMode = gameMode;

        playerOrder = new ArrayList<>(gameModel.getPlayerIds());
        System.out.println("Player order at initialization:\n"+playerOrder);
        //Collections.shuffle(playerOrder);
        currentPlayerIndex = 0;
    }

    public void firstOrderShuffle(){
        Collections.shuffle(playerOrder);
        System.out.println("Player order after shuffle:\n"+playerOrder);
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

    public boolean isGameOver(){
        ArrayList<UUID> playerIds = gameModel.getPlayerIds();
        for (UUID player: playerIds){
            try {
                int initialNumOfTowers = gameModel.getPlayerIds().size() == 2? 8:6;
                if (initialNumOfTowers - gameModel.getNumOfTowers(player) == 0 || gameModel.countIslands() == 3 ||
                        gameModel.countStudentsInBag() == 0 || (gameModel.getDeckSize(player) == 0 && currentPlayerIndex < playerOrder.size()-1 && phase == TurnState.ACTION)) return true;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public UUID getGameWinner(){
        if (!isGameOver()) return null;
        ArrayList<UUID> playerIds = gameModel.getPlayerIds();
        for (UUID player: playerIds){
            int initialNumOfTowers = gameModel.getPlayerIds().size() == 2? 8:6;
            if(initialNumOfTowers - gameModel.getNumOfTowers(player)==0) return player;
            else{
                //return player with most towers or if tie player with most professors among the players with most towers
                ArrayList<UUID> playersWithMostTowers = getPlayersWithMostTowers();
                if (playersWithMostTowers.size() == 1) return playersWithMostTowers.get(0);
                return getPlayerWithMostProfessors(playersWithMostTowers);
            }
        }
        return null;
    }

    private ArrayList<UUID> getPlayersWithMostTowers(){
        int maxTowers = 0;
        boolean tie = false;
        UUID maxPlayer = null;
        for (UUID player : gameModel.getPlayerIds()){
            int currTowers = gameModel.getNumOfTowers(player);
            if (currTowers>maxTowers){
                maxTowers = currTowers;
                maxPlayer = player;
                tie = false;
            } else if (currTowers == maxTowers){
                tie = true;
            }
        }
        ArrayList<UUID> list = new ArrayList<>();
        if (!tie) {
            list.add(maxPlayer);
        } else {
            for (UUID player : gameModel.getPlayerIds()){
                if (gameModel.getNumOfTowers(player) == maxTowers) list.add(player);
            }
        }
        return list;
    }

    private UUID getPlayerWithMostProfessors(ArrayList<UUID> amongPlayers){
        int maxProfessors = 0;
        UUID maxPlayer = null;
        for (UUID player : amongPlayers){
            int currProfessors = 0;
            for (PawnColor color: PawnColor.values()){
                if (gameModel.getProfessorOwners().get(color).equals(player)) currProfessors++;
            }
            if (currProfessors>maxProfessors){
                maxProfessors = currProfessors;
                maxPlayer = player;
            }
        }
        return maxPlayer;
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
        } else if (modelEvent instanceof  ChosenCharacter){
            AvailableCharacter character = ((ChosenCharacter) modelEvent).getChosenCharacter();

            try {
                lastCharacter = gameModel.payAndGetCharacter(getCurrentPlayer(), character);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }

            //TODO: just save the character somewhere and use it in new method after setting up player, island, color
            //lastPlayedCharacter = ((ChosenCharacter) modelEvent).getChosenCharacter();
        } else if (modelEvent instanceof MovedStudent){
            UUID island = ((MovedStudent) modelEvent).getIslandId();
            PawnColor color = ((MovedStudent) modelEvent).getColor();
            if (island == null){
                try {
                    gameModel.moveStudentToDining(color, getCurrentPlayer());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    gameModel.moveStudentToIsland(color, getCurrentPlayer(), island);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            //lastChosenStudent = ((MovedStudent) modelEvent).getColor();
            //lastChosenIsland = ((MovedStudent) modelEvent).getIslandId();
        } else if (modelEvent instanceof MovedMotherNature){
            try {
                gameModel.moveMotherNature(((MovedMotherNature) modelEvent).getMotherNatureSteps(), getCurrentPlayer());
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            //lastMotherNatureSteps = ((MovedMotherNature) modelEvent).getMotherNatureSteps();
        } else if (modelEvent instanceof  ChosenCloud){
            try {
                gameModel.moveCloudToEntrance(((ChosenCloud) modelEvent).getCloud(), getCurrentPlayer());
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            if(phase == TurnState.ACTION && currentPlayerIndex >= playerOrder.size()-1){
                gameModel.fillAllClouds();
                gameModel.clearPlayedAssistantCards();
            }
            //lastChosenCloud = ((ChosenCloud) modelEvent).getCloud();
        } else if (modelEvent instanceof SetCharacterSettings){
            PawnColor color = ((SetCharacterSettings) modelEvent).getColor();
            UUID island = ((SetCharacterSettings) modelEvent).getIsland();
            ArrayList<ColorSwap> colorSwaps = ((SetCharacterSettings) modelEvent).getColorSwaps();

            if (lastCharacter instanceof EffectWithColor){
                ((EffectWithColor) lastCharacter).setEffectColor(color);
            }
            if (lastCharacter instanceof EffectWithIsland){
                ((EffectWithIsland) lastCharacter).setEffectIsland(island);
            }
            if (lastCharacter instanceof EffectWithPlayer){
                ((EffectWithPlayer) lastCharacter).setEffectPlayer(getCurrentPlayer());
            }
            if (lastCharacter instanceof SwapperCharacter){
                for (ColorSwap swap : colorSwaps){
                    ((SwapperCharacter) lastCharacter).setupColorSwaps(swap.getGive(), swap.getTake());
                }

            }


            try {
                lastCharacter.useEffect();
            } catch (NoSuchFieldException | IllegalStateException e) {
                //e.printStackTrace();
                System.out.println("the effect is being used improperly, so nothing will happen");
            }
        }
    }
}
