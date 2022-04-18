package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.lang.reflect.Array;
import java.rmi.server.UID;

public class RoundController {
    private GameModel gameModel;
    private GameController gameController;
    private IslandManager islandManager;
    private MessageHandler messageHandler;
    int size;
    int prevPlayerPlayedCard;
    int currPlayerPlayedCard;
    int arrayOfPlayedCards[];
    int arrayOfOrderedPlayers[];


    public RoundController() {
    }


    public void startRound(int player) {
        //PLANNING:
        size = messageHandler.getNumOfPlayers();
        arrayOfOrderedPlayers = new int[size];
        arrayOfOrderedPlayers[0] = player;
        int firstPlayer = gameController.getPlayerIndex();
        for(int i=1; i<size;i++){
            arrayOfOrderedPlayers[i] = firstPlayer+i;
            if(firstPlayer+i == size){
                arrayOfOrderedPlayers[i]=0;
            }else if(firstPlayer+1 > size){
                arrayOfOrderedPlayers[i]=1;
            }
        }
        gameModel.fillAllClouds();
        arrayOfPlayedCards = new int[size];

        for(int i=0; i<size; i++){
            arrayOfPlayedCards[i] = messageHandler.playAssistantCard();
            //TODO: check that currPlayedCard is different from prev
        }

        /* prevPlayerPlayedCard = messageHandler.playAssistantCard();
        for(int i = 0; i<size-1; i++){
            currPlayerPlayedCard = messageHandler.playAssistantCard();
            if(currPlayerPlayedCard == prevPlayerPlayedCard){
               int newCurrCard = messageHandler.playAssistantCard();
               currPlayerPlayedCard = newCurrCard;
            }
        }
         */
         //now we have two arrays: first one has the players in order of play, second the played card - with corresponding indexes

        //now: we check the values of the played cards in arrayOfPlayedCards; we find the lowest
        //we reorder the arrayOfOrderedPlayers: new position 0 for player with same index as lowest playedCard number - next in order




        /* - sends message to first randomly chosen player messageHandler.sentMessage(player)
        and observe what user does (choose an Assistant Card)
        it then notifies gameModel which removes that card from the player's deck
        calls messageHandler again for other player(s) and does same thing

        if(player1.cardID bigger than player2.cardID)  player1 = first player
        else player2
        else equal error

        ACTION: from player with lower ID in order of increasing value
        messageHandler.sendMessage(player) - has to choose how to move 3 students to isleTiles or diningRoom
        then modify GameBoard , professor check
        second message about moving motherNature returns int -- check island owner
        second player again with all checks

        end of first round, returns player with lower ID - is the one that is going to start in next round
         */
    }
    public void endRound() {
    }
    public void nextRound(int player) {
    }
    public void endGame() {
    }





    }


