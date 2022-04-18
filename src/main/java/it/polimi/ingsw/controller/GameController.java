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

    private String playerNickname;
    private List<String> listOfPlayers;



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
        int i;
        List<String > listOfPlayers = new ArrayList<>();

        for(i=0; i<numPlayers;i++){

            playerNickname = messageHandler.setNickname();
            listOfPlayers.add(i, playerNickname);

            //gameModel.initPlayer() - playerNickname is Player nickname
        }

        }


    public void setup(){
        //has numOfPlayers and gameMode - calls game model and island manager
        /*gameModel.init(numPlayers) - in gameModel method with init for number of clouds, players and so on
        that has variation for 2 or 3 players
        islandManager.init() - not necessary number of players as a parameter
         */
    }

    public void start() {

        //returns the randomly selected player
        //calls round: smt like round.firstRound(playerRandomlyChosen)
        }

    public void endGame(){

    }


    }


