package it.polimi.ingsw.client;

import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientGUI {  //CONTROLLER

   @FXML
    private TextField serverIpBox;
    @FXML
    private TextField serverPortBox;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ServerHandler serverHandler;
    private ViewInterface guiView;

    private String message = "";
    //private ServerHandlerGUI serverHandlerGUI;




    private Stage stage;
    private Scene scene;
    private Parent root;


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

    private String selectNickname(){
        return nickname.getText();
    }

    public void buttonSetNickname(){
        String s = selectNickname();



    }






}
