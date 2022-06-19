package it.polimi.ingsw.client;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;
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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class GuiView implements ViewInterface {
    private EventManager<ViewEvent> eventManager;
    private Scanner scanner;
    private final ClientGui clientGui;
    private GameState gameState;

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

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void sendAcknowledgement() {
        System.out.println("Acknowledgement received by server on guiView");
    }

    @Override
    public void askPlayAgain() {

    }

    @Override
    public void chooseCharacter() {
        clientGui.setNextSceneName("/GameBoard2.fxml");
    }

    @Override
    public void chooseCloud() {

    }

    @Override
    public void getAssistantCard() {
        clientGui.setNextSceneName("/ChooseAssistantCard.fxml");

    }

    @Override
    public void invalidAssistantCard() {
        System.out.println("Invalid assistant card");
        getAssistantCard();
    }

    @Override
    public void getNickname(){
        clientGui.setNextSceneName("/SetNickname.fxml");
    }

    @Override
    public void getPreferences() {
        clientGui.setNextSceneName("/SetPreferences.fxml");
    }

    @Override
    public void letsPlay() {
        System.out.println("Waiting for other players...");
        notifyEventManager(new ReadyToPlay());
    }

    @Override
    public void playerTurn() {

    }

    @Override
    public void invalidCharacterChoice() {
        System.out.println("invalid character choice");
        chooseCharacter();
    }

    @Override
    public void invalidMNMove() {
        System.out.println("invalid mother nature move");
        moveMotherNature();
    }

    @Override
    public void invalidNickname() {
        System.out.println("invalid nickname");
        getNickname();
    }

    @Override
    public void invalidStudentMove() {
        System.out.println("invalid student move");
        moveStudent();
    }

    @Override
    public void moveMotherNature() {
        clientGui.setNextSceneName("/GameBoard2.fxml");
    }

    @Override
    public void moveStudent() {
        clientGui.setNextSceneName("/GameBoard2.fxml");
    }

    @Override
    public void gameOver(UUID winner) {

    }

    @Override
    public void getCharacterSettings(AvailableCharacter forCharacter) {

    }

    public void notifyEventManager(ViewEvent event){
        eventManager.notify(event);
    }

    @Override
    public void update(ModelEvent modelEvent) {
        gameState = (GameState) modelEvent;
    }
}
