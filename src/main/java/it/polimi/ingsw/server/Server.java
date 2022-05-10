package it.polimi.ingsw.server;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.controller.GameController;
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


        /* Read the port number from System.in. In your project you
         * could also use a configuration file for the same purpose.
         * For simplicity, we are not doing any error checking when
         * parsing the port number, but in a real project you need to
         * always handle invalid inputs! */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server port?");
        int socketPort = Integer.parseInt(scanner.nextLine());

        ServerSocket socket;
        try {
            socket = new ServerSocket(socketPort);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        while (true) {
            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                VirtualView virtualView = new VirtualView(client, gameController, gameController.getTurnController());
                modelEventManager.subscribe(virtualView);
                Thread thread = new Thread(virtualView, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}
