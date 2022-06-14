package it.polimi.ingsw.client;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.networkmessages.ReceivedByClient;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.Handshake;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ServerHandlerGUI implements Runnable, EventListener<ViewEvent> {
    private Thread thread;
    private Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ClientGUIFirst owner;
    private ViewInterface view;
    private EventManager<ModelEvent> eventManager;
    private AtomicBoolean shouldStop = new AtomicBoolean(false);

    public ServerHandlerGUI(Socket server, ClientGUIFirst owner){
        this.server = server;
        this.owner = owner;
        eventManager = new EventManager<>();

    }

    //TODO: restructure ServerHandler so it can be applied both to CLI and GUI

    public void linkGuiView(ViewInterface view){
        this.view = view;
        eventManager.subscribe(view);
    }


    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            handleConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public void handleConnection() throws IOException{
        try {
            boolean stop = false;
            output.writeObject(new Handshake());

            while(!stop){
                try {
                    Object next = input.readObject();
                    ReceivedByClient message = (ReceivedByClient) next;
                    message.processMessage(view, eventManager);
                } catch (IOException e) {
                    /* Check if we were interrupted because another thread has asked us to stop */
                    if (shouldStop.get()) {
                        /* Yes, exit the loop gracefully */
                        stop = true;
                    } else {
                        /* No, rethrow the exception */
                        throw e;
                    }
                }

            }

        } catch (ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(ViewEvent viewEvent) {
        try {
            output.writeObject(viewEvent);
        } catch (IOException e) {
            System.out.println("Communication error");
            //owner.terminate();
        }
    }
}








