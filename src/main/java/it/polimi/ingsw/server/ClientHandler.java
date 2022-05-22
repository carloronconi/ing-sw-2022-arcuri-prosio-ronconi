package it.polimi.ingsw.server;

import it.polimi.ingsw.networkmessages.controllercalls.*;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private VirtualView virtualView;

    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void assignVirtualView(VirtualView virtualView){
        this.virtualView = virtualView;
    }

    public void sendAcknowledgement() {
        try {
            output.writeObject(new Acknowledgement());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   protected void writeObject(RemoteMethodCall remoteMethodCall){
        try {
            output.writeObject(remoteMethodCall);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void forwardModel(ModelEvent modelEvent){
        try {
            output.writeObject(modelEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println("output stream created");
            input = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("input stream created");
        } catch (IOException e) {
            System.out.println("could not open connection to " + clientSocket.getInetAddress());
            return;
        }

        try {
            while (true) {

                Object next = input.readObject();
                ViewEvent message = (ViewEvent) next;
                //command.processMessage(this);
                try{
                    try {
                        message.processMessage(virtualView);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (InvalidObjectException e){
                    e.printStackTrace();
                }

            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        } catch (IOException e) {
            System.out.println("could not open connection to " + clientSocket.getInetAddress());
            return;
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
