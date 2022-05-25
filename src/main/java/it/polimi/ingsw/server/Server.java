package it.polimi.ingsw.server;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {

        EventManager<ModelEvent> modelEventManager = new EventManager<>();
        GameController gameController = new GameController(modelEventManager);

        Scanner scanner = new Scanner(System.in);

        ServerSocket socket;
        while(true){
            System.out.println("Server port?");
            //int socketPort = Integer.parseInt(scanner.nextLine());
            int socketPort = 5000;

            try {
                socket = new ServerSocket(socketPort);
                break;
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("Can't open server socket! Try with a different port:");
            }
        }

        System.out.println("Server active! Waiting for clients...");

        while (true) {
            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                VirtualView virtualView = new VirtualView(gameController, clientHandler);
                clientHandler.assignVirtualView(virtualView);
                modelEventManager.subscribe(virtualView);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }


    }


}
