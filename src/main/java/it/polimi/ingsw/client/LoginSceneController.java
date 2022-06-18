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


    public void connectButtonClicked(ActionEvent event) //button at the end of loginScene
    {
        getClientGui().setIp(ipSet());
        getClientGui().setPort(portSet());
        getClientGui().run();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eryantisFirstScene.fxml"));
        try {
            Parent root = loader.load(); //show LET'S PLAY scene
            LetsPlaySceneController controller = loader.getController();
            controller.setClientGui(getClientGui());
            Scene scene = new Scene(root, 800, 530);
            Stage stage = getClientGui().getStage();
            stage.setTitle("ERYANTIS");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
