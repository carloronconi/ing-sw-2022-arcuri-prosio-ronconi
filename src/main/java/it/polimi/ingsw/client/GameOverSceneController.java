package it.polimi.ingsw.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverSceneController extends SceneController {
    public Label winner;

    public void setup(){
        String winnerName = getClientGui().getGuiView().getWinner();
        String message = winnerName == null? "Nobody!" : winnerName;
        winner.setText(message);
    }
}
