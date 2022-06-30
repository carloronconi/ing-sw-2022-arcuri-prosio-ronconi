package it.polimi.ingsw.client.gui;

import java.io.IOException;

/**
 * Class to show to the player the starting image of the game
 */
public class LetsPlaySceneController extends  SceneController{
    public void starting() throws IOException { //button at the end of eryantisFirstScene - the one with let's play
        new ChangeScene(getClientGui()).run();

    }
}
