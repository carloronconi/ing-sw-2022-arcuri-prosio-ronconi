package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.viewevents.SetAssistantCard;
import it.polimi.ingsw.networkmessages.viewevents.SetNickname;
import it.polimi.ingsw.networkmessages.viewevents.SetPreferences;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientGui extends Application implements Runnable{

    private ServerHandler serverHandler;
    private Socket server;
    private static GuiView guiView;

    //Login view
    @FXML
    private TextField serverIpBox;
    @FXML
    private TextField serverPortBox;

    //Nickname view
    @FXML
    private TextField nickname;
    private static String finalNickname;

    //Preferences view
    @FXML
    private RadioButton button2;
    @FXML
    private RadioButton button3;
    @FXML
    private RadioButton buttonEasy;
    @FXML
    private RadioButton buttonHard;

    //Set assistant card view
    int cardNumber;
    @FXML
    Button button;
    @FXML
    ImageView card1;
    @FXML ImageView card2;
    @FXML ImageView card3;
    @FXML ImageView card4;
    @FXML ImageView card5;
    @FXML ImageView card6;
    @FXML ImageView card7;
    @FXML ImageView card8;
    @FXML ImageView card9;
    @FXML ImageView card10;
    @FXML
    private ImageView lastPlayed1;
    @FXML
    public ImageView playedByOther;
    static ArrayList<String> playedByOtherResources = new ArrayList<>();

    private Parent root;
    private Scene scene;
    private static Stage stage;
    private static String nextSceneName = "";

    public static void main(String[] args){  launch(args);  }



    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml")); //scene with id and port
        scene = new Scene(root, 800, 530);
        stage.setTitle("ERYANTIS");
        stage.setScene(scene);
        stage.show();


    }


    public String ipSet(){
        return serverIpBox.getText();
    }

    public int portSet(){
        return Integer.parseInt(serverPortBox.getText());
    }


    @Override
    public void run() {
        String ip = ipSet();
        int port = portSet();

        try {
            server = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverHandler = new ServerHandler(server);

        guiView = new GuiView(serverHandler, this);
        serverHandler.linkView(guiView);

        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();

    }

    public void setNextSceneName(String nextScene) { //called when the server is ready and the scene can be changed to the next one
        synchronized (ClientGui.class) {
            while (!nextSceneName.isEmpty()) {
                try {
                    ClientGui.class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            nextSceneName = nextScene;
            ClientGui.class.notifyAll();
        }
    }

    private void nextScene() throws IOException {
        boolean isAssistantScene;
        FXMLLoader fxmlLoader;
        synchronized (ClientGui.class){
            while(nextSceneName.isEmpty()){ //wait until the serverHandler allows to go to the next scene
                try {
                    ClientGui.class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            fxmlLoader = new FXMLLoader(getClass().getResource(nextSceneName));
            root = fxmlLoader.load(); //show next scene with the name selected by serverHandler
            isAssistantScene= nextSceneName.equals("/ChooseAssistantCard.fxml");
            nextSceneName = "";
            ClientGui.class.notifyAll();
        }


        scene = new Scene(root, 800, 530);
        //stage.setTitle("Nickname");
        stage.setScene(scene);
        stage.show();

        if (isAssistantScene){
            ClientGui controllerInstance = (ClientGui) fxmlLoader.getController();
            additionalAssistantCardSetup(controllerInstance);
        }
    }

    private void additionalAssistantCardSetup(ClientGui controllerInstance){
        GameState gameState = guiView.getGameState();
        HashMap<UUID, String> nicknames = gameState.getNicknames();

        ArrayList<UUID> otherIds = new ArrayList<>();

        Iterator<Map.Entry<UUID, String>> iterator = nicknames.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<UUID, String> entry = iterator.next();
            if (!entry.getValue().equals(finalNickname)) otherIds.add(entry.getKey());
        }

        ArrayList<Integer> otherCards = new ArrayList<>();
        HashMap<UUID, Integer> playedAssistantCards = gameState.getPlayedAssistantCards();
        for (UUID id : otherIds){
            if (playedAssistantCards.get(id)!=null) otherCards.add(gameState.getPlayedAssistantCards().get(id));
        }
        if (otherCards.isEmpty()) return; //it means it is the first player that is choosing the assistant

        playedByOtherResources.add("/Assistente(" + otherCards.get(0) + ")-min.png");
        //TODO: do the same for otherPlayer2 when there are 3 players

        controllerInstance.addPlayedAssistantCards();
        //playedByOther.setImage(new Image(String.valueOf(getClass().getResource(playedByOtherResource1))));
        //card10.setOpacity(0.4d); would be nice to make the cards played by others opaque so that it's clear that you can't choose it
    }

    private void addPlayedAssistantCards(){
        if (playedByOther!=null && !playedByOtherResources.isEmpty()) {
            //TODO: for 3 player game also set the other image
            playedByOther.setImage(new Image(String.valueOf(getClass().getResource(playedByOtherResources.get(0)))));
        }
    }


    public void connectButtonClicked(ActionEvent event) //button at the end of loginScene
    {
        run();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eryantisFirstScene.fxml"));
        try {
            root = loader.load(); //show LET'S PLAY scene
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root, 800, 530);
        stage.setTitle("ERYANTIS");
        stage.setScene(scene);
        stage.show();


    }

    public void starting() throws IOException { //button at the end of eryantisFirstScene - the one with let's play
        nextScene();
    }

    public void buttonSetNickname(ActionEvent event) throws IOException { //button at the end of setNickname scene
        finalNickname = nickname.getText();
        guiView.notifyEventManager(new SetNickname(finalNickname));
        nextScene();
    }


    public void buttonClick(ActionEvent event) throws IOException { //button at the end of set preferences scene
        int numOfPlayers;
        GameMode gameMode;
        numOfPlayers = button2.isSelected() ? 2 : 3;
        gameMode = buttonEasy.isSelected() ? GameMode.EASY : GameMode.HARD;
        guiView.notifyEventManager(new SetPreferences(numOfPlayers, gameMode));
        nextScene();
    }

    public void clickedButton(ActionEvent e) throws IOException { //button at the end of set assistant card scene
        guiView.notifyEventManager(new SetAssistantCard(cardNumber));
        nextScene();
        /*GameBoardController c = new GameBoardController();

        root = FXMLLoader.load(getClass().getResource("/GameBoard2.fxml"));

        scene = new Scene(root, 1500, 876);
        scene.setOnMouseMoved((evt) -> c.mouseMoved(evt));
        scene.setOnMouseDragged((evt)->c.mouseMoved(evt));
        stage = new Stage();
        stage.setTitle("Game Board");
        stage.setScene(scene);
        stage.show();*/


    }





    public void chosenCard1(){
        number(card1);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(1)-min.png"))));
        card1.setOpacity(0.4d);
    }
    public void chosenCard2(){
        number(card2);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(2)-min.png"))));
        card2.setOpacity(0.4d);
    }
    public void chosenCard3(){
        number(card3);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(3)-min.png"))));
        card3.setOpacity(0.4d);
    }
    public void chosenCard4(){
        number(card4);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(4)-min.png"))));
        card4.setOpacity(0.4d);
    }
    public void chosenCard5(){
        number(card5);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(5)-min.png"))));
        card5.setOpacity(0.4d);
    }
    public void chosenCard6(){
        number(card6);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(6)-min.png"))));
        card6.setOpacity(0.4d);
    }
    public void chosenCard7(){
        number(card7);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(7)-min.png"))));
        card7.setOpacity(0.4d);
    }
    public void chosenCard8(){
        number(card8);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(8)-min.png"))));
        card8.setOpacity(0.4d);
    }
    public void chosenCard9(){
        number(card9);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(9)-min.png"))));
        card9.setOpacity(0.4d);
    }
    public void chosenCard10(){
        number(card10);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(10)-min.png"))));
        card10.setOpacity(0.4d);
    }

    private void number(ImageView imageView) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(imageView.toString());
        while (m.find()) {
            cardNumber = Integer.parseInt(m.group());
            System.out.println(cardNumber);

        }

    }


}
