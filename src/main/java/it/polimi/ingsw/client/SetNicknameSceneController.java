package it.polimi.ingsw.client;

import it.polimi.ingsw.networkmessages.viewevents.SetNickname;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;

public class SetNicknameSceneController extends SceneController{

    @FXML
    private TextField nickname;


    public void buttonSetNickname(ActionEvent event) throws IOException { //button at the end of setNickname scene
        String finalNickname = nickname.getText();
        getClientGui().setFinalNickname(finalNickname);
        getClientGui().getGuiView().notifyEventManager(new SetNickname(finalNickname));
        getClientGui().nextScene((s, c)->{
            if (c instanceof SetAssistantSceneController){
                SetAssistantSceneController controller = (SetAssistantSceneController) c;
                ArrayList<String> resources = getClientGui().getPlayedByOtherResources();
                if (!resources.isEmpty()){
                    controller.getPlayedByOther().setImage(new Image(String.valueOf(getClass().getResource(resources.get(0)))));
                    if (controller.getPlayedBySecondOther()!=null && resources.size()>1) controller.getPlayedBySecondOther().setImage(new Image(String.valueOf(getClass().getResource(resources.get(1)))));
                }
            }
        });
    }
}
