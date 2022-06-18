package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.networkmessages.viewevents.SetPreferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

import java.io.IOException;

public class SetPreferencesSceneController extends SceneController {
    //Preferences view
    @FXML
    private RadioButton button2;
    @FXML
    private RadioButton button3;
    @FXML
    private RadioButton buttonEasy;
    @FXML
    private RadioButton buttonHard;

    public void buttonClick(ActionEvent event) throws IOException { //button at the end of set preferences scene
        int numOfPlayers;
        GameMode gameMode;
        numOfPlayers = button2.isSelected() ? 2 : 3;
        gameMode = buttonEasy.isSelected() ? GameMode.EASY : GameMode.HARD;
        getClientGui().getGuiView().notifyEventManager(new SetPreferences(numOfPlayers, gameMode));
        getClientGui().nextScene(1500, 876, "ERYANTIS", (s, c)->{});

    }

}
