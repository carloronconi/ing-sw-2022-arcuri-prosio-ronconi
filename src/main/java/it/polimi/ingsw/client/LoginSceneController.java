package it.polimi.ingsw.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSceneController extends SceneController{
    @FXML
    private TextField serverIpBox;
    @FXML
    private TextField serverPortBox;

    public String ipSet(){
        return serverIpBox.getText();
    }

    public int portSet(){
        return Integer.parseInt(serverPortBox.getText());
    }

    /**
     * Method to send the IP address and the Port set by the client to start a connection with the server
     * @param event when the user clicks the button
     */
    public void connectButtonClicked(ActionEvent event) //button at the end of loginScene
    {
        getClientGui().setIp(ipSet());
        getClientGui().setPort(portSet());
        getClientGui().run();
        if (getClientGui().isFailedRun()) return;
        new ChangeScene(getClientGui()).run();

    }
}
