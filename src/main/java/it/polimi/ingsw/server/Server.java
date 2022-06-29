package it.polimi.ingsw.server;

import it.polimi.ingsw.utilities.EventManager;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.networkmessages.controllercalls.GameOver;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Server {
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private boolean stop;
    private int numOfPlayers = 3;
    private ServerSocket socket;

    public static void main(String[] args) {

        Server server = new Server();
        System.exit(0);
    }

    public Server() {

        EventManager<ModelEvent> modelEventManager = new EventManager<>();
        GameController gameController = new GameController(modelEventManager);

        Scanner scanner = new Scanner(System.in);


        while(true){
            System.out.println("Server port?");

            try {
                int socketPort = Integer.parseInt(scanner.nextLine());
                //int socketPort = 4999;

                socket = new ServerSocket(socketPort);
                break;
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("Can't open server socket! Try with a different port:");
            }
        }

        System.out.println("Server active! Waiting for clients...");

        while (!stop) {
            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                if (clientHandlers.size()<getNumOfPlayers()){
                    ClientHandler clientHandler = new ClientHandler(client, this);
                    VirtualView virtualView = new VirtualView(gameController, clientHandler, this);
                    clientHandler.assignVirtualView(virtualView);
                    modelEventManager.subscribe(virtualView);
                    Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                    clientHandlers.add(clientHandler);
                    thread.start();
                } else {
                    ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                    output.writeObject(new GameOver(null));
                    client.close();
                }
            } catch (IOException e) {
                System.out.println("connection dropped");
                gameIsOver(null);
            }
        }


    }

    public void gameIsOver(UUID winner, ClientHandler brokenClient){
        if (clientHandlers.isEmpty()) return;
        ArrayList<ClientHandler> clientHandlerList = new ArrayList<>(clientHandlers);
        System.out.println(clientHandlerList);
        if (brokenClient!=null) clientHandlerList.remove(brokenClient);
        System.out.println(clientHandlerList);
        for (ClientHandler clientHandler : clientHandlerList){
            clientHandler.writeObject(new GameOver(winner));
            //stop the thread
            clientHandler.stopClient();
        }
        clientHandlers.clear();
        System.out.println("Stopping server");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Socket closed");
        stop = true;
    }

    public void gameIsOver(UUID winner){
        gameIsOver(winner, null);
    }

    public synchronized void setNumOfPlayers(int numOfPlayers){
        this.numOfPlayers=numOfPlayers;
        System.out.println(numOfPlayers);
    }

    private synchronized int getNumOfPlayers(){
        return numOfPlayers;
    }
}
