package it.polimi.ingsw.server;

import it.polimi.ingsw.networkmessages.ReceivedByClient;
import it.polimi.ingsw.networkmessages.controllercalls.*;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.modelevents.ServerHeartbeat;
import it.polimi.ingsw.networkmessages.viewevents.Heartbeat;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private VirtualView virtualView;
    private final Server server;
    private boolean stop;

    public ClientHandler(Socket clientSocket, Server server){
        this.clientSocket = clientSocket;
        this.server = server;
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


   protected void writeObject(ReceivedByClient message){
        try {
            output.writeObject(message);
        } catch (IOException e) {
            if (!(message instanceof GameOver)) server.gameIsOver(null, this);
            //e.printStackTrace();
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
            e.printStackTrace();
            return;
        }

        new Thread(()->{
            try {
                while(true){
                    output.writeObject(new ServerHeartbeat());
                    TimeUnit.SECONDS.sleep(3);
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("Client disconnected");
                server.gameIsOver(null, this);
            }
        }).start();

        try {
            while (!stop) {

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
            server.gameIsOver(null, this);
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopClient(){
        stop = true;
    }
}
