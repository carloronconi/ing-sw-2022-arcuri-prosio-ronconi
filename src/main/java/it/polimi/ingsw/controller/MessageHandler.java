package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class MessageHandler {
    //an attribute refers to the view - player nickname so that I have a getter
    private int numOfPlayer;
    private int gameMode; //maybe not necessary to have an Enum for the gameMode
    Scanner s = new Scanner(System.in);
    private String name;



    public void getCurrentPlayerID() {
    }

    public void sendMessage( /*message, player*/) {
    }

    public int howManyMessage() {
        System.out.println("How many players?");
        Scanner s = new Scanner(System.in);
        numOfPlayer = s.nextInt();
        while (numOfPlayer <= 0 || numOfPlayer > 3) {

            System.out.println("Number of players not valid, try again");
            System.out.println("How many players?");
            numOfPlayer = s.nextInt();
        }

        System.out.println("Chosen game mode with number of players: " + numOfPlayer);

        return numOfPlayer;
    }



    public String setNickname() {
            try {
                System.out.println("Write your nickname: ");
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String name = br.readLine();

            } catch (IOException ioe) {
                System.out.println("IO exception raised\n");
            }

            return name;

        }



    public int whichGameMode(){
        System.out.println("Which game mode do you want to play with?\n State 0 for easy, 1 for expert");
        gameMode = s.nextInt();
        //exception if number the user inputted is nor 0 or 1;
        //do it later
        if(gameMode == 0){
            System.out.println("Chosen EASY game mode");
            return 0;
        }else{
            System.out.println("Chosen EXPERT game mode");
            return 1;
        }


    }


}

