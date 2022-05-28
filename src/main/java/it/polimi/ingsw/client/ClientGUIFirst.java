package it.polimi.ingsw.client;

import it.polimi.ingsw.ViewInterface;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientGUIFirst extends Application {
    private static ServerHandlerGUI serverHandlerGUI;
    private ClientGUI clientGUI = new ClientGUI();

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
    private static ViewInterface guiView;

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



    public void connectButtonClicked(ActionEvent event) throws IOException
    {
        Socket server;
        server = new Socket(ipSet(), portSet());
        serverHandlerGUI = new ServerHandlerGUI(server, this);
        serverHandlerGUI.run();

        guiView = new GuiView(serverHandlerGUI);
        serverHandlerGUI.linkGuiView(guiView);


        root = FXMLLoader.load(getClass().getResource("/eryantisFirstScene.fxml"));
        scene = new Scene(root, 800, 530);
        stage = new Stage();
        stage.setTitle("ERYANTIS");
        stage.setScene(scene);
        stage.show();


    }

    public void starting() throws IOException {
        guiView.sendAcknowledgement();
        root = FXMLLoader.load(getClass().getResource("/SetNickname.fxml"));
        scene = new Scene(root, 800, 530);
        stage = new Stage();
        stage.setTitle("Nickname");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private TextField nickname;

    private String selectNickname(){
        return nickname.getText();
    }

    public void buttonSetNickname() throws IOException {
        String s = selectNickname();
        serverHandlerGUI.forwardMessage(s);


    }











}
