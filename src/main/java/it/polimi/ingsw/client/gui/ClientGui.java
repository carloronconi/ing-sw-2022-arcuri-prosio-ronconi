package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import javafx.application.Application;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.Socket;
import java.util.*;


public class ClientGui extends Application implements Runnable{

    private ServerHandler serverHandler;
    private Socket server;
    private static GuiView guiView;
    private String ip;
    private int port;
    private String finalNickname;
    private Stage stage;
    private String nextSceneName = "";
    private boolean failedRun = false;

    public static void main(String[] args){  launch(args);  }



    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        new ChangeScene("/LoginScene.fxml", this, 800, 530, "ERYANTIS",
                ((scene1, controller1) -> {
                    stage.setResizable(false);
                    stage.show();
                    stage.setOnCloseRequest((t)->{
                        System.exit(0);
                    });
                })).run();

    }

    public Stage getStage() {
        return stage;
    }

    public void setFinalNickname(String finalNickname) {
        this.finalNickname = finalNickname;
    }

    public String getFinalNickname() {
        return finalNickname;
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

    public boolean isFailedRun() {
        return failedRun;
    }


    @Override
    public void run() {

        try {
            server = new Socket(ip, port);
            serverHandler = new ServerHandler(server);

            guiView = new GuiView(serverHandler, this);
            serverHandler.linkView(guiView);

            Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
            serverHandlerThread.start();
        } catch (IOException e) {
            failedRun = true;
        }

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
            for (int card : otherCards){
                resources.add("/Assistente(" + card + ")-min.png");
            }
        }
        return resources;
    }


}