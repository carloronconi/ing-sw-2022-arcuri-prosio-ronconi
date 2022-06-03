package it.polimi.ingsw.client;

import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.controller.GameMode;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.PseudoColumnUsage;
import java.util.ResourceBundle;


public class ClientGUIFirst extends Application implements Runnable{
    private ServerHandlerGUI serverHandlerGUI;
    private ClientGUI clientGUI;
    private Socket server;
    private static GuiView guiView;


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
    private ServerHandler serverHandler;


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
        serverHandlerGUI = new ServerHandlerGUI(server, this);

        guiView = new GuiView(serverHandlerGUI, this);
        serverHandlerGUI.linkGuiView(guiView);

        Thread serverHandlerThread = new Thread(serverHandlerGUI, "server_" + server.getInetAddress().getHostAddress());
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

        gameBoardController c = new gameBoardController();

        root = FXMLLoader.load(getClass().getResource("/GameBoard2.fxml"));

        scene = new Scene(root, 1560, 900);
        scene.setOnMouseMoved((evt) -> c.mouseMoved(evt));
        scene.setOnMouseDragged((evt)->c.mouseMoved(evt));
        stage = new Stage();
        stage.setTitle("Game Board");
        stage.setScene(scene);
        stage.show();



    }



}
