package it.polimi.ingsw.client;

import it.polimi.ingsw.utilities.EventListener;
import it.polimi.ingsw.utilities.EventManager;
import it.polimi.ingsw.utilities.ViewInterface;
import it.polimi.ingsw.networkmessages.ReceivedByClient;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.Handshake;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A class that represents the server inside the client.
 */
public class ServerHandler implements Runnable, EventListener<ViewEvent> {
    private Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean stop;
    private EventManager<ModelEvent> eventManager;
    private ViewInterface view;





    /**
     * Initializes a new handler using a specific socket connected to
     * a server.
     * @param server The socket connection to the server.
     */
    public ServerHandler(Socket server)
    {
        this.server = server;
        eventManager = new EventManager<>();

    }

    public void linkView(ViewInterface view){
        this.view = view;
        eventManager.subscribe(view);
    }


    /**
     * Connects to the server and runs the event loop.
     */
    @Override
    public void run()
    {
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            System.out.println("output stream created");
            input = new ObjectInputStream(server.getInputStream());
            System.out.println("input stream created");
        } catch (IOException e) {
            System.out.println("could not open connection to " + server.getInetAddress());
            //e.printStackTrace();
            view.gameOver(null);
            stopServer();
            //owner.terminate();
            return;
        }

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("server " + server.getInetAddress() + " connection dropped");
            view.gameOver(null);
            stopServer();
        }

        try {
            server.close();
        } catch (IOException e) { }
    }


    /**
     * An event loop that receives messages from the server and processes
     * them in the order they are received.
     * @throws IOException If a communication error occurs.
     */
    private void handleClientConnection() throws IOException
    {
        try {
            output.writeObject(new Handshake());

            while (!stop) {
                try {
                    Object next = input.readObject();
                    ReceivedByClient message = (ReceivedByClient) next;
                    message.processMessage(view, eventManager);
                } catch (IOException e) {
                    System.out.println("Communication error");
                    view.gameOver(null);
                    stopServer();
                }
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from server");
            view.gameOver(null);
            stopServer();
        }
    }


    public void stopServer(){
        stop = true;
    }
    @Override
    public void update(ViewEvent viewEvent) {
        try {
            output.writeObject(viewEvent);
        } catch (IOException e) { //happens to the client that is currently making a move when server dies
            //e.printStackTrace();
            System.out.println("Communication error");
            view.gameOver(null);
            stopServer();
        }
    }
}