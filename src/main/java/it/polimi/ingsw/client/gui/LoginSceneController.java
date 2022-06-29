package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginSceneController extends SceneController{
    @FXML
    private TextField serverIpBox;
    @FXML
    private TextField serverPortBox;

    public String ipSet(){
        return serverIpBox.getText();
    }

    public int portSet(){
        try{
            return Integer.parseInt(serverPortBox.getText());
        } catch (IllegalArgumentException e){
            return 0;
        }
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
