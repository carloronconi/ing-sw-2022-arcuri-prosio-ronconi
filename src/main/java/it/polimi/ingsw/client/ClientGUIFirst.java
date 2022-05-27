package it.polimi.ingsw.client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGUIFirst extends Application {
    private ServerHandlerGUI serverHandlerGUI = new ServerHandlerGUI();
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











}
