package it.polimi.ingsw.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeScene implements Runnable{
    private String nextSceneName;
    private ClientGui clientGui;
    private int sceneWidth;
    private int sceneHeight;
    private String stageTitle;
    private SceneInitializer initializer;

    /*
    public enum DefaultWaitScene{
        STANDARD(""),
        GAME_BOARD
    }*/


    public ChangeScene(ClientGui clientGui) {
        this("/WaitingScene.fxml",clientGui, 800, 530, "ERYANTIS", ((scene, controller) -> {}));
    }

    /*
    public ChangeScene(ClientGui clientGui, DefaultWaitScene defaultType) {
        this(nextSceneName,clientGui, 1500, 876, "ERYANTIS", ((scene, controller) -> {}));
    }*/

    public ChangeScene(String nextSceneName, ClientGui clientGui) {
        this(nextSceneName,clientGui, 1500, 876, "ERYANTIS", ((scene, controller) -> {}));
    }

    public ChangeScene(String nextSceneName, ClientGui clientGui, int sceneWidth, int sceneHeight) {
        this(nextSceneName,clientGui, sceneWidth, sceneHeight, "ERYANTIS", ((scene, controller) -> {}));
    }

    public ChangeScene(String nextSceneName, ClientGui clientGui,SceneInitializer initializer) {
        this(nextSceneName,clientGui, 1500, 876, "ERYANTIS", initializer);
    }

    public ChangeScene(String nextSceneName, ClientGui clientGui, int sceneWidth, int sceneHeight, String stageTitle, SceneInitializer initializer) {
        this.nextSceneName = nextSceneName;
        this.clientGui = clientGui;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.stageTitle = stageTitle;
        this.initializer = initializer;
    }

    @Override
    public void run() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(nextSceneName));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (fxmlLoader.getController() instanceof SceneController) ((SceneController) fxmlLoader.getController()).setClientGui(clientGui);
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        initializer.initializeScene(scene, fxmlLoader.getController());
        clientGui.getStage().setScene(scene);
        clientGui.getStage().setTitle(stageTitle);
        //clientGui.getStage().show();
    }
}
