package it.polimi.ingsw.client;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
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
import java.util.function.Consumer;

public class ServerHandlerGUI implements Runnable, EventListener<ViewEvent> {
    private Thread thread;
    private Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ClientGUIFirst owner;
    private ViewInterface view;
    private EventManager<ModelEvent> eventManager;

    public ServerHandlerGUI(Socket server, ClientGUIFirst owner){
        this.server = server;
        this.owner = owner;
        eventManager = new EventManager<>();

    }

    //TODO: restructure ServerHander so it can be applied both to CLI and GUI

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
            output.writeObject(new Handshake());
        } catch (IOException e) {
            e.printStackTrace();
        }

            handleConnection();


    }

    public void forwardMessage(String s) throws IOException {
        Object object = s;
        output.writeObject(object);
    }

    public void handleConnection(){
        try {
            boolean stop = false;
            output.writeObject(new Handshake());
        } catch (IOException e) {
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








