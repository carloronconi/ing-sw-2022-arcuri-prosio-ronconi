package it.polimi.ingsw.client;

import java.io.IOException;

public class LetsPlaySceneController extends  SceneController{
    public void starting() throws IOException { //button at the end of eryantisFirstScene - the one with let's play
        SetNicknameSceneController controller = (SetNicknameSceneController) getClientGui().nextScene(800, 530, "ERYANTIS", (s, c)->{});
        controller.setClientGui(getClientGui());
    }
}
