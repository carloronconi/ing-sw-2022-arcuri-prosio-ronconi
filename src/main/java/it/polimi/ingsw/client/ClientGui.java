package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.viewevents.SetAssistantCard;
import it.polimi.ingsw.networkmessages.viewevents.SetNickname;
import it.polimi.ingsw.networkmessages.viewevents.SetPreferences;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientGui extends Application implements Runnable{

    private ServerHandler serverHandler;
    private Socket server;
    private static GuiView guiView;
    private String ip;
    private int port;
    private String finalNickname;
    private Stage stage;
    private String nextSceneName = "";

    public static void main(String[] args){  launch(args);  }



    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;/*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginScene.fxml"));
        Parent root = loader.load(); //scene with id and port
        LoginSceneController controller = loader.getController();
        controller.setClientGui(this);
        Scene scene = new Scene(root, 800, 530);
        stage.setTitle("ERYANTIS");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();*/

        new ChangeScene("/LoginScene.fxml", this, 800, 530, "ERYANTIS",
                ((scene1, controller1) -> {stage.setResizable(false);})).run();

    }

    public Stage getStage() {
        return stage;
    }

    public void setFinalNickname(String finalNickname) {
        this.finalNickname = finalNickname;
    }

    public GuiView getGuiView(){
        return guiView;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        //String ip = ipSet();
        //int port = portSet();

        try {
            server = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverHandler = new ServerHandler(server);

        guiView = new GuiView(serverHandler, this);
        serverHandler.linkView(guiView);

        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();

    }
    /*
    public synchronized void setNextSceneName(String nextScene) { //called when the server is ready and the scene can be changed to the next one
        System.out.println("starting setNextSceneName method");
        while (!nextSceneName.isEmpty()) {
            try {
                System.out.println("nextSceneName is empty");
                wait(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        nextSceneName = nextScene;
        notifyAll();
        System.out.println("setNextSceneName to " +nextScene);
    }


    public Object nextScene() throws IOException {
        return nextScene((s, c)->{});
    }

    public Object nextScene(SceneInitializer initializer) throws IOException {
        return nextScene(1500, 876, "ERYANTIS", initializer);
    }

    public Object nextScene(int sceneWidth, int sceneHeight, String stageTitle, SceneInitializer initializer) throws IOException {
        return nextScene(sceneWidth, sceneHeight, stageTitle, initializer, "/WaitingScene.fxml");
    }

    public synchronized Object nextScene(int sceneWidth, int sceneHeight, String stageTitle, SceneInitializer initializer, String waitSceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(waitSceneName));
        Parent root = fxmlLoader.load();
        Scene waitScene = new Scene(root, sceneWidth, sceneHeight);
        stage.setScene(waitScene);
        stage.show();

        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                System.out.println("task started");
                while (nextSceneName.isEmpty()){
                    System.out.println("nextSceneName is empty");
                    ClientGui.this.wait(5000);
                }
                String name = nextSceneName;
                nextSceneName = "";
                //ClientGui.this.notifyAll();
                System.out.println("task completed next scene is: " + name);
                return name;
            }
        };

        task.setOnSucceeded(e->{
            System.out.println("starting task succeeded sequence");
            String name = task.getValue();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
            Parent r = null; //show next scene with the name selected by serverHandler
            try {
                r = loader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (loader.getController() instanceof SceneController) ((SceneController) loader.getController()).setClientGui(this);
            Scene scene = new Scene(r, sceneWidth, sceneHeight);
            initializer.initializeScene(scene, loader.getController());
            stage.setTitle(stageTitle);
            stage.setScene(scene);
            stage.show();
            System.out.println("task succeeded sequence ends next scene loaded");
        });

        new Thread(task).start();

        return null;
    }*/

    public String getFinalNickname(){ return finalNickname;}

    public ArrayList<String> getPlayedByOtherResources(){
        ArrayList<String> resources = new ArrayList<>();

        GameState gameState = guiView.getGameState();
        HashMap<UUID, String> nicknames = gameState.getNicknames();

        ArrayList<UUID> otherIds = new ArrayList<>();

        Iterator<Map.Entry<UUID, String>> iterator = nicknames.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<UUID, String> entry = iterator.next();
            if (!entry.getValue().equals(finalNickname)) otherIds.add(entry.getKey());
        }

        ArrayList<Integer> otherCards = new ArrayList<>();
        HashMap<UUID, Integer> playedAssistantCards = gameState.getPlayedAssistantCards();
        for (UUID id : otherIds){
            if (playedAssistantCards.get(id)!=null) otherCards.add(gameState.getPlayedAssistantCards().get(id));
        }
        if (!otherCards.isEmpty()) { //it means it is the first player that is choosing the assistant
            for (int card : otherCards){
                resources.add("/Assistente(" + card + ")-min.png");
            }
        }
        return resources;
    }


}
