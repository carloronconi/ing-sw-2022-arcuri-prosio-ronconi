package it.polimi.ingsw.client;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.SetAssistantCard;
import it.polimi.ingsw.networkmessages.viewevents.SetNickname;
import it.polimi.ingsw.networkmessages.viewevents.SetPreferences;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class GuiView implements ViewInterface {
          private EventManager<ViewEvent> eventManager;
          private Scanner scanner;
          private ClientGui clientGui;

          //private JTextField introductionField;
          //private JTextArea screenArea;
         // private String message = "";
         // private String serverChat;


   public GuiView(ServerHandler serverHandler, ClientGui clientGui){
    eventManager = new EventManager<>();
    eventManager.subscribe(serverHandler);
    scanner = new Scanner(System.in);

    this.clientGui = clientGui;

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

    public void getAssistantCard(int cardNumber){
           eventManager.notify(new SetAssistantCard(cardNumber));
    }

    @Override
    public void invalidAssistantCard() {

    }

    @Override
    public void getNickname(){
    //  String name = clientGUIFirst.selectNickname();
     // eventManager.notify(new SetNickname(name));

    }

    public void getNickname(String s) {
      eventManager.notify(new SetNickname(s));

    }

    public void getPreferences(int players, GameMode mode){
      eventManager.notify(new SetPreferences(players, mode));
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
 public void invalidStudentMove() {

 }

 @Override
    public void moveMotherNature() {

    }

    @Override
    public void moveStudent() {

    }

 @Override
 public void gameOver(UUID winner) {

 }

 @Override
 public void getCharacterSettings(AvailableCharacter forCharacter) {

 }






}
