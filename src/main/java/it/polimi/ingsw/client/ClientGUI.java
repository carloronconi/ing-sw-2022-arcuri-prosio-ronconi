package it.polimi.ingsw.client;

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


public class ClientGUI {  //CONTROLLER

    private static ClientGUI currentApplication;
    private ServerHandlerGUI serverHandlerGUI = new ServerHandlerGUI();

    @FXML
    private TextField serverIpBox;
    @FXML
    private TextField serverPortBox;



    public static ClientGUI getCurrentApplication()
    {
        return currentApplication;
    }


    public ServerHandlerGUI getServerHandlerGUI()
    {
        return serverHandlerGUI;
    }


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
        //Switch scene;
        serverHandlerGUI.openConnection(ipSet(), portSet());

        root = FXMLLoader.load(getClass().getResource("/eryantisFirstScene.fxml"));
        scene = new Scene(root, 800, 530);
        stage = new Stage();
        stage.setTitle("ERYANTIS");
        stage.setScene(scene);
        stage.show();


    }




    /*SWITCH TO LOGO AFTER CONNECTION
    Parent root = FXMLLoader.load(getClass().getResource("eryantisFirstScene.fxml"));
        Scene scene = new Scene(root, 800, 530);
        stage.setTitle("ERYANTIS");
        stage.setScene(scene);
        stage.show();
     */


}
