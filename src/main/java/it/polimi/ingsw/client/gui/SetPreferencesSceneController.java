package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.server.controller.GameMode;
import it.polimi.ingsw.networkmessages.viewevents.SetPreferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

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

    private ToggleGroup numberGroup = new ToggleGroup();
    private ToggleGroup modeGroup = new ToggleGroup();


    @FXML
    public void initialize(){
        button2.setToggleGroup(numberGroup);
        button3.setToggleGroup(numberGroup);
        buttonEasy.setToggleGroup(modeGroup);
        buttonHard.setToggleGroup(modeGroup);
    }

    public void buttonClick(ActionEvent event) throws IOException { //button at the end of set preferences scene
        int numOfPlayers;
        GameMode gameMode;

        numOfPlayers = numberGroup.getSelectedToggle() == button2 ? 2 : 3;
        gameMode = modeGroup.getSelectedToggle() == buttonEasy ? GameMode.EASY : GameMode.HARD;
        getClientGui().getGuiView().notifyEventManager(new SetPreferences(numOfPlayers, gameMode));
        new ChangeScene(getClientGui()).run();

    }

}
