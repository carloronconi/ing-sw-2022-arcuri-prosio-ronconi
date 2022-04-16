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



    public void getCurrentPlayerID() {
    }

    public void sendMessage( /*message, player*/) {
    }

    public int howManyMessage() {
        int numOfPlayer = 0;
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

    public void setNickname() {
        int i= 0;
        List<String> listOfPlayers = new ArrayList<>();
        while(i < numOfPlayer) {
            try {
                System.out.println("Write your nickname: ");
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String name = br.readLine();
                System.out.format("Player " +i+ ": %s", name);
                listOfPlayers.add(i, name);

            } catch (IOException ioe) {
                System.out.println("IO exception raised\n");
            }

            i++;
        }

    }


}

