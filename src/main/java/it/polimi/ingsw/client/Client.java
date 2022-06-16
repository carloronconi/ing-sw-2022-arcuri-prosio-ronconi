package it.polimi.ingsw.client;

import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.client.ServerHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


/**
 * Client for the Mastermind game.
 */
public class Client implements Runnable {
    private ServerHandler serverHandler;
    private boolean shallTerminate;
    private ViewInterface view;



    public static void main(String[] args) {
        /* Instantiate a new Client. The main thread will become the
         * thread where user interaction is handled. */
        Client client = new Client();
        client.run();

    }


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Socket server;
        while(true){
            System.out.println("IP address of server?");
            String ip = scanner.nextLine();
            //String ip = "localhost";

            System.out.println("Server port?");
            int socketPort = Integer.parseInt(scanner.nextLine());
            //int socketPort = 4999;

            try {
                server = new Socket(ip, socketPort);
                break;
            } catch (IOException e) {
                System.out.println("Server unreachable! Try with different address/port:");
            }
        }

        serverHandler = new ServerHandler(server, this);


            view = new CliView(serverHandler);
            serverHandler.linkView(view);


        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
        serverHandlerThread.start();

        /* Run the state machine handling the views */
        //nextView = new NextNumberView();
        //runViewStateMachine();

        /* We are going to stop the application, so ask the server thread
         * to stop as well. Note that we are invoking the stop() method on
         * ServerHandler, not on Thread */
        //serverHandler.stop();
    }

}