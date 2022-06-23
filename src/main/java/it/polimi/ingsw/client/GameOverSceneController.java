package it.polimi.ingsw.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverSceneController extends SceneController {
    public Label winner;

    @FXML
    public void initialize(){
        winner.setText(getClientGui().getGuiView().getWinner());
    }
}
