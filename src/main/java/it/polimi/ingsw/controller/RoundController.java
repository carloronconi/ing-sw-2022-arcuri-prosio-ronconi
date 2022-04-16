package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.rmi.server.UID;

public class RoundController {
    private GameModel gameModel;
    private IslandManager islandManager;
    private MessageHandler messageHandler;

    public RoundController() {
    }


    public void startRound( /*randomlyChosenPlayer first, then the player from check from assistant card*/) { //ret player
        /* PLANNING:
        gameModel.fillAllClouds();
        calls messageHandler - sends message to first randomly chosen player messageHandler.sentMessage(player)
        and observe what user does (choose an Assistant Card)
        it then notifies gameModel which removes that card from the player's deck
        calls messageHandler again for other player(s) and does same thing

        if(player1.cardID bigger than player2.cardID)  player1 = first player
        else player2
        else equal error

        ACTION: from player with lower ID in order of increasing value
        messageHandler.sendMessage(player) - has to choose how to move 3 students to isleTiles or diningRoom
        then modify GameBoard , professor check
        second message about moving mothernature returns int -- check island owner
        second player again with all checks

        end of first round, returns player with lower ID - is the one that is going to start in next round
         */
    }
    public void endRound() {
    }
    public void nextRound() {
    }
    public void endGame() {
    }





    }


