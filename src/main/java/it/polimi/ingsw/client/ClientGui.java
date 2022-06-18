package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.viewevents.SetAssistantCard;
import it.polimi.ingsw.networkmessages.viewevents.SetNickname;
import it.polimi.ingsw.networkmessages.viewevents.SetPreferences;
import javafx.application.Application;
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
    private static String finalNickname;
    private static Stage stage;
    private static String nextSceneName = "";

    public static void main(String[] args){  launch(args);  }



    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginScene.fxml"));
        Parent root = loader.load(); //scene with id and port
        LoginSceneController controller = loader.getController();
        controller.setClientGui(this);
        Scene scene = new Scene(root, 800, 530);
        stage.setTitle("ERYANTIS");
        stage.setScene(scene);
        stage.show();


    }

    public static Stage getStage() {
        return stage;
    }

    public static void setFinalNickname(String finalNickname) {
        ClientGui.finalNickname = finalNickname;
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

    public void setNextSceneName(String nextScene) { //called when the server is ready and the scene can be changed to the next one
        synchronized (ClientGui.class) {
            while (!nextSceneName.isEmpty()) {
                try {
                    ClientGui.class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            nextSceneName = nextScene;
            ClientGui.class.notifyAll();
        }
    }

    public Object nextScene(int sceneWidth, int sceneHeight, String stageTitle, SceneInitializer initializer) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader;
        synchronized (ClientGui.class){
            while(nextSceneName.isEmpty()){ //wait until the serverHandler allows to go to the next scene
                try {
                    ClientGui.class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            fxmlLoader = new FXMLLoader(getClass().getResource(nextSceneName));
            root = fxmlLoader.load(); //show next scene with the name selected by serverHandler
            nextSceneName = "";
            ClientGui.class.notifyAll();
        }


        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        initializer.initializeScene(scene, fxmlLoader.getController());
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();

        if (fxmlLoader.getController() instanceof SceneController) ((SceneController) fxmlLoader.getController()).setClientGui(this);
        return fxmlLoader.getController();
    }

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
            resources.add("/Assistente(" + otherCards.get(0) + ")-min.png");
        }
        return resources;
    }


}
