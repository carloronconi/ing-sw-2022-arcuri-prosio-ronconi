package it.polimi.ingsw.client;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class GuiView implements ViewInterface {
          private EventManager<ViewEvent> eventManager;
         // private JTextField introductionField;
          //private JTextArea screenArea;
         // private String message = "";
         // private String serverChat;


   public GuiView(ServerHandler serverHandler){
    eventManager = new EventManager<>();
    eventManager.subscribe(serverHandler);


    //introductionField = new JTextField();
   // introductionField.setEditable( false );



   }
    @Override
    public void update(ModelEvent modelEvent) {

    }

    @Override
    public void sendAcknowledgement() {

    }

    @Override
    public void askPlayAgain() {

    }

    @Override
    public void chooseCharacter() {

    }

    @Override
    public void chooseCloud() {

    }

    @Override
    public void getAssistantCard() {

    }

    @Override
    public void invalidAssistantCard() {

    }

    @Override
    public void getNickname() {

    }

    @Override
    public void getPreferences() {

    }

    @Override
    public void letsPlay() {

    }

    @Override
    public void playerTurn() {

    }

    @Override
    public void invalidCharacterChoice() {

    }

    @Override
    public void invalidMNMove() {

    }

    @Override
    public void invalidNickname() {

    }

    @Override
    public void moveMotherNature() {

    }

    @Override
    public void moveStudent() {

    }

    @Override
    public void gameOver() {

    }

    @Override
    public void getColorSwap() {

    }

    @Override
    public void getColorChoice() {

    }

    @Override
    public void getIslandChoice() {

    }
}
