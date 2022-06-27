package it.polimi.ingsw.client.gui;

import javafx.scene.control.Label;

public class GameOverSceneController extends SceneController {
    public Label winner;

    public void setup(){
        String winnerName = getClientGui().getGuiView().getWinner();
        String message = winnerName == null? "Nobody!" : winnerName;
        winner.setText(message);
    }
}
