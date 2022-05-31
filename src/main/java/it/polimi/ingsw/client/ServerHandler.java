package it.polimi.ingsw.client;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.ReceivedByClient;
import it.polimi.ingsw.networkmessages.controllercalls.RemoteMethodCall;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.Handshake;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class that represents the server inside the client.
 */
public class ServerHandler implements Runnable, EventListener<ViewEvent> {
    private Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Client owner;
    private boolean stop;
    private EventManager<ModelEvent> eventManager;
    private ViewInterface view;


    /**
     * Initializes a new handler using a specific socket connected to
     * a server.
     * @param server The socket connection to the server.
     */
    public ServerHandler(Socket server, Client owner)
    {
        this.server = server;
        this.owner = owner;
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
            e.printStackTrace();
            //owner.terminate();
            return;
        }

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("server " + server.getInetAddress() + " connection dropped");
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
                    stop = true;
                }
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from server");
        }
    }


    /**
     * The game instance associated with this client.
     * @return The game instance.
     */
    public Client getClient()
    {
        return owner;
    }

    public void stopServer(){
        stop = true;
    }
    @Override
    public void update(ViewEvent viewEvent) {
        try {
            output.writeObject(viewEvent);
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Communication error");
            stopServer();
        }
    }
}