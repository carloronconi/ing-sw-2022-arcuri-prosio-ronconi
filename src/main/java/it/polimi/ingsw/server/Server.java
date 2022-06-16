package it.polimi.ingsw.server;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.networkmessages.controllercalls.GameOver;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Server {
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private boolean stop;

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
            int socketPort = Integer.parseInt(scanner.nextLine());
            //int socketPort = 4999;

            try {
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
                ClientHandler clientHandler = new ClientHandler(client, this);
                VirtualView virtualView = new VirtualView(gameController, clientHandler, this);
                clientHandler.assignVirtualView(virtualView);
                modelEventManager.subscribe(virtualView);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                clientHandlers.add(clientHandler);
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
                gameIsOver(null);
            }
        }


    }

    public void gameIsOver(UUID winner){
        for (ClientHandler clientHandler : clientHandlers){
            clientHandler.writeObject(new GameOver(winner));
            //stop the thread
            clientHandler.stopClient();
        }
        System.out.println("Stopping server");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Socket closed");
        stop = true;
    }
}
