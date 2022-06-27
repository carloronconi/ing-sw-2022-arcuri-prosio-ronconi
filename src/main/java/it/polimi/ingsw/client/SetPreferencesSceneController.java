package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.networkmessages.viewevents.SetPreferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;

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

    /**
     * Method to set up the scene and have it as two ToggleGroups: number of players and game mode
     */
    @FXML
    public void initialize(){
        button2.setToggleGroup(numberGroup);
        button3.setToggleGroup(numberGroup);
        buttonEasy.setToggleGroup(modeGroup);
        buttonHard.setToggleGroup(modeGroup);
    }

    /**
     * This method saves the preferences selected by the player (number of players and game mode) in local variables and
     * sends a new message to the server containing these settings
     *
     * @param event when the player clicks the button
     * @throws IOException
     */
    public void buttonClick(ActionEvent event) throws IOException { //button at the end of set preferences scene
        int numOfPlayers;
        GameMode gameMode;

        numOfPlayers = numberGroup.getSelectedToggle() == button2 ? 2 : 3;
        gameMode = modeGroup.getSelectedToggle() == buttonEasy ? GameMode.EASY : GameMode.HARD;
        getClientGui().getGuiView().notifyEventManager(new SetPreferences(numOfPlayers, gameMode));
        new ChangeScene(getClientGui()).run();

    }

}
