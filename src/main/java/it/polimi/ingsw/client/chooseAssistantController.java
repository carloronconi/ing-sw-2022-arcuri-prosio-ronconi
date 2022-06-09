package it.polimi.ingsw.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class chooseAssistantController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button button;

    public void clickedButton(ActionEvent e) throws IOException {

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
