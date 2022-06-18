package it.polimi.ingsw.client;

import it.polimi.ingsw.networkmessages.viewevents.SetNickname;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SetNicknameSceneController extends SceneController{

    @FXML
    private TextField nickname;


    public void buttonSetNickname(ActionEvent event) throws IOException { //button at the end of setNickname scene
        String finalNickname = nickname.getText();
        getClientGui().setFinalNickname(finalNickname);
        getClientGui().getGuiView().notifyEventManager(new SetNickname(finalNickname));
        getClientGui().nextScene(800, 530, "ERYANTIS", (s, c)->{});
    }
}
