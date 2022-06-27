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
        return Integer.parseInt(serverPortBox.getText());
    }


    public void connectButtonClicked(ActionEvent event) //button at the end of loginScene
    {
        getClientGui().setIp(ipSet());
        getClientGui().setPort(portSet());
        getClientGui().run();
        if (getClientGui().isFailedRun()) return;
        new ChangeScene(getClientGui()).run();

    }
}
