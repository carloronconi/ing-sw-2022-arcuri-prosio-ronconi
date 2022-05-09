package it.polimi.ingsw.server;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.networkmessages.controllercalls.Acknowledgement;
import it.polimi.ingsw.networkmessages.controllercalls.AskPlayAgain;
import it.polimi.ingsw.networkmessages.controllercalls.GetNickname;
import it.polimi.ingsw.networkmessages.controllercalls.GetPreferences;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class VirtualView implements ViewInterface, EventListener<ModelEvent>, Runnable {
    private Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private EventManager<ViewEvent> eventManager;
    private static int numberOfInstances = 0;
    private final int thisInstanceNumber;
    private GameController gameController;

    public VirtualView(Socket clientSocket, GameController gameController, TurnController turnController) {
        this.clientSocket = clientSocket;
        this.gameController = gameController;
        eventManager = new EventManager<>();
        eventManager.subscribe(turnController);
        eventManager.subscribe(gameController);
        thisInstanceNumber = numberOfInstances;
        numberOfInstances++;
    }

    public int getThisInstanceNumber() {
        return thisInstanceNumber;
    }

    public boolean isNicknameAlreadyUsed(String nickname){
        return gameController.getPlayerNicknames().contains(nickname);
    }

    @Override
    public void sendAcknowledgement() {
        try {
            output.writeObject(new Acknowledgement());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askPlayAgain() {
        try {
            output.writeObject(new AskPlayAgain());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void chooseCharacter() {

    }

    @Override
    public void chooseCloud() {

    }

    @Override
    public void getAssistantCard() {

    }

    @Override
    public void invalidAssistantCard() {

    }


    @Override
    public void getNickname() {
        try {
            output.writeObject(new GetNickname());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPreferences() {
        try {
            output.writeObject(new GetPreferences());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playerTurn() {

    }

    @Override
    public void invalidCharacterChoice() {

    }

    @Override
    public void invalidMNMove() {

    }

    @Override
    public void invalidNickname() {

    }

    @Override
    public void moveMotherNature() {

    }

    @Override
    public void moveStudent() {

    }

    @Override
    public void gameOver() {

    }

    @Override
    public void getColorSwap() {

    }

    @Override
    public void getColorChoice() {

    }

    @Override
    public void getIslandChoice() {

    }

    @Override
    public void update(ModelEvent modelEvent) {
        //forward the modelEvent through the socket
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
            input = new ObjectInputStream(clientSocket.getInputStream());
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
                    message.processMessage(this);
                    eventManager.notify(message);
                } catch (InvalidObjectException e){ }

            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        } catch (IOException e) {
            System.out.println("could not open connection to " + clientSocket.getInetAddress());
            return;
        }

        try {
            clientSocket.close();
        } catch (IOException e) { }

    }
}
