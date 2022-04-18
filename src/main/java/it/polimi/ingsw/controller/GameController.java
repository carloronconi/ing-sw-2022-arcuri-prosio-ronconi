package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.studentmanagers.IslandManager;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private GameModel gameModel;         //everything
    private IslandManager islandManager; //island tiles
    //private GameMode gameMode;         //INSTEAD OF ENUM IN MESSAGE HANDLER INT (0: EASY, 1: EXPERT)
    private RoundController round;
    private MessageHandler messageHandler;
    private int numPlayers;


    public int getNumOfPlayers(){
        //messageHandler is the link to the view package and returns number of players chosen by user
        numPlayers = messageHandler.howManyMessage();
        return numPlayers;
    }

    public void whichGameMode() {
       if(messageHandler.whichGameMode() == 0){
                    //initialisation gameModel bank : 0 - else 20
                    //build this init in GameModel so that GameController can call it
                    //gameModel.
       }else{
           //initialisation to 20
       }


    }

    public void setupPlayers(){
        messageHandler.setNickname();
        int i;
        ArrayList<Player> players = new ArrayList<Player>();
        //TODO: link arrayList of players by nickname from messageHandler to GameModel

        }


    public void setup(){
        //has numOfPlayers and gameMode - calls game model and island manager
        /*gameModel.init(numPlayers) - in gameModel method with init for number of clouds, players, island tiles and MN and so on
        that has variation for 2 or 3 players
         */
    }

    public void start() {
        //first: randomly choose one of the players as the one to start getRandom which
        //returns the randomly selected player
        //calls round: smt like round.firstRound(playerRandomlyChosen)
        }

    public void endGame(){

    }


    }


