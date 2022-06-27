package it.polimi.ingsw.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverSceneController extends SceneController {
    public Label winner;

    /**
     * Method shows to the players the winner according to the server.
     * If something went wrong - the connection dropped or a player was disconnected to the game - the scene is shown to the
     * other players stating "nobody" as winner
     */
    public void setup(){
        String winnerName = getClientGui().getGuiView().getWinner();
        String message = winnerName == null? "Nobody!" : winnerName;
        winner.setText(message);
    }
}
