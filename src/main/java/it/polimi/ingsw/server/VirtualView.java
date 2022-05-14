package it.polimi.ingsw.server;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.networkmessages.controllercalls.*;
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
    private static int whoseTurn;
    private final int thisInstanceNumber;
    private final GameController gameController;
    private TurnController turnController;

    private void writeObject(RemoteMethodCall remoteMethodCall){
        try {
            output.writeObject(remoteMethodCall);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VirtualView(Socket clientSocket, GameController gameController) {
        this.clientSocket = clientSocket;
        this.gameController = gameController;
        eventManager = new EventManager<>();
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

    public void subscribeToEventManager(TurnController turnController){
        this.turnController = turnController;
        eventManager.subscribe(turnController);
    }

    public synchronized boolean isItMyTurn(){
       // updateNextTurn();
        return thisInstanceNumber == whoseTurn;
    }

  /*  private synchronized void updateNextTurn(){
        // change whoseTurn variable according to player order in turnController
        whoseTurn = turnController.getNextPlayer();
        notifyAll();
    }*/

    public boolean isAssistantCardIllegal(int card){
        return gameController.isAssistantCardIllegal(card, thisInstanceNumber);
    }

    @Override
    public void sendAcknowledgement() {
        writeObject(new Acknowledgement());
    }

    @Override
    public void askPlayAgain() {
        writeObject(new AskPlayAgain());
    }

    @Override
    public void chooseCharacter() {

    }

    @Override
    public void chooseCloud() {

    }

    @Override
    public void getAssistantCard() {
        writeObject(new GetAssistantCard());
    }

    @Override
    public void invalidAssistantCard() {
        writeObject(new InvalidAssistantCard());

    }


    @Override
    public void getNickname() {
        writeObject(new GetNickname());
    }

    @Override
    public void getPreferences() {
        writeObject(new GetPreferences());
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
        writeObject(new InvalidNickname());
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
                        //doubt about this try-catch
                        message.processMessage(this);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    eventManager.notify(message);
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
