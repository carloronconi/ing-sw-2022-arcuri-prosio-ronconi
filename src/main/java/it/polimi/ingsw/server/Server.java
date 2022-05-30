package it.polimi.ingsw.server;

import com.sun.security.jgss.GSSUtil;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.networkmessages.controllercalls.GameOver;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.currentThread;

public class Server {
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private boolean stop;

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
            int socketPort = 4999;

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
                VirtualView virtualView = new VirtualView(gameController, clientHandler);
                clientHandler.assignVirtualView(virtualView);
                modelEventManager.subscribe(virtualView);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                clientHandlers.add(clientHandler);
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
                gameIsOver();
            }
        }


    }

    public void gameIsOver(){
        for (ClientHandler clientHandler : clientHandlers){
            clientHandler.writeObject(new GameOver(null));
            //stop the thread
            clientHandler.stopClient();
        }
        System.out.println("Stopping server");
        stop = true; //TODO: Server actually keeps running
    }
}
