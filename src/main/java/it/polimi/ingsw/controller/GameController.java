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
    private GameMode gameMode;
    private RoundController round;
    private MessageHandler messageHandler;


    public int getNumOfPlayer(){
        //messageHandler is the link to the view package and returns number of players chosen by user
        int numPlayers =0;
        return numPlayers;
    }

    public void setup(){
        //has numOfPlayers and gameMode - calls game model and island manager

    }

    public void setupPlayers(String nickname, int playerID){
        //calls messageHandler
    }

    public void sendMessage(){
        //calls message handler
    }

    public void start() {
        //first: randomly choose one of the players as the one to start getrandom which
        //returns the randomly selected player
        //calls round: smt like round.firstRound(playerRandomlyChosen)
        }
    public void endGame(){

    }


    }


