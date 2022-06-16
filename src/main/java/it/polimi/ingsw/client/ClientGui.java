package it.polimi.ingsw.client;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.networkmessages.GenericEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientGui extends Application implements Runnable{
    private ServerHandler serverHandler;
    private Socket server;
    private static GuiView guiView;
    private static ChooseAssistantController chooseAssistantController;

    public static void main(String[] args){  launch(args);  }



    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
        Scene scene = new Scene(root, 800, 530);
        stage.setTitle("ERYANTIS");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    private TextField serverIpBox;
    @FXML
    private TextField serverPortBox;

    private ObjectInputStream input;
    private ObjectOutputStream output;


    private String message = "";

    public String ipSet(){
        return serverIpBox.getText();
    }

    public int portSet(){
        return Integer.parseInt(serverPortBox.getText());
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

  /*  public void setConnection(){
        serverHandlerGUI.openConnection(ipSet(),portSet());
    } */

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


    public void connectButtonClicked(ActionEvent event) throws IOException
    {
        run();
        root = FXMLLoader.load(getClass().getResource("/eryantisFirstScene.fxml"));
        scene = new Scene(root, 800, 530);
        stage = new Stage();
        stage.setTitle("ERYANTIS");
        stage.setScene(scene);
        stage.show();


    }

    public void starting() throws IOException {

        root = FXMLLoader.load(getClass().getResource("/SetNickname.fxml"));
        scene = new Scene(root, 800, 530);
        stage = new Stage();
        stage.setTitle("Nickname");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private TextField nickname;

    public String selectNickname(){
        return nickname.getText();
    }



    public void buttonSetNickname(ActionEvent event) throws IOException {
       String s = selectNickname();
       guiView.getNickname(s);


        root = FXMLLoader.load(getClass().getResource("/SetPreferences.fxml"));
        scene = new Scene(root, 800, 530);
        stage = new Stage();
        stage.setTitle("Nickname");
        stage.setScene(scene);
        stage.show();



    }
    public int numOfPlayersG;
    public GameMode gameModeG;


    @FXML
    private RadioButton button2;
    @FXML
    private RadioButton button3;
    @FXML
    private RadioButton buttonEasy;
    @FXML
    private RadioButton buttonHard;



    public boolean playersSelectedTwo(){
        if(button2.isSelected()) return true;
        return false;
    }

    public boolean gameModeSelectedEasy(){
        if(buttonEasy.isSelected()) return true;
        return false;
    }



    public void buttonClick(ActionEvent event) throws IOException{
        if(playersSelectedTwo()) numOfPlayersG = 2; else numOfPlayersG = 3;
        if(gameModeSelectedEasy()) gameModeG = GameMode.EASY; else gameModeG = GameMode.HARD;
        guiView.getPreferences(numOfPlayersG, gameModeG);

        ChooseAssistantController chooseAssistantController = new ChooseAssistantController(this);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChooseAssistantCard.fxml"));
        loader.setController(chooseAssistantController);

        root = loader.load();





        //root = FXMLLoader.load(getClass().getResource("/ChooseAssistantCard.fxml"));
        scene = new Scene(root, 1440, 850);
        stage = new Stage();
        stage.setTitle("ERYANTIS");
        stage.setScene(scene);
        stage.show();



    }


    public void updateChosenAssistant(int numOfAssistant){
        guiView.getAssistantCard(numOfAssistant);
        System.out.println("tutto ok "+numOfAssistant);
        //TODO: interleave this method to ChooseAssistantController
    }


  /*  @Override
    public void update(Integer integer) {
        guiView.getAssistantCard(integer);
        System.out.println("tutto ok"+integer);
    }*/



}
