package it.polimi.ingsw.server;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.TurnState;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.controllercalls.*;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class VirtualView implements EventListener<ModelEvent> , ViewInterface {
    private Socket clientSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private EventManager<ViewEvent> eventManager;
    private static int numberOfInstances = 0;
    private final int thisInstanceNumber;
    private final GameController gameController;
    private TurnController turnController ;
    public UUID id;
    private final ClientHandler clientHandler;

   /* private void writeObject(RemoteMethodCall remoteMethodCall){
        try {
            output.writeObject(remoteMethodCall);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public VirtualView(GameController gameController, ClientHandler clientHandler) {
        //this.clientSocket = clientSocket;
        this.gameController = gameController;
        eventManager = new EventManager<>();
        eventManager.subscribe(gameController);
        thisInstanceNumber = numberOfInstances;
        numberOfInstances++;
        this.clientHandler = clientHandler;
    }

    public UUID getId(){
        return id;
    }

    public static int getNumberOfInstances() {
        return numberOfInstances;
    }

    public GameMode getGameMode(){return gameController.getGameMode();}
    public TurnState getTurnControllerState(){ return turnController.getCurrentPhase();}

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
        return id == turnController.getCurrentPlayer();
    }

    public void playerFinishedTurn(){
        turnController.playerFinishedTurn();
    }

    public boolean isAssistantCardIllegal(int card){
        return gameController.isAssistantCardIllegal(card, thisInstanceNumber);
    }

    public boolean isCharacterCardIllegal(AvailableCharacter card){
        return gameController.isCharacterCardIllegal(card, thisInstanceNumber);
    }

    //@Override
    public void sendAcknowledgement() {
        clientHandler.writeObject(new Acknowledgement());
    }

    @Override
    public void askPlayAgain() {
        clientHandler.writeObject(new AskPlayAgain());
    }

    @Override
    public void chooseCharacter() {
        clientHandler.writeObject(new ChooseCharacter());

    }
    @Override
    public void chooseCloud() {
        clientHandler.writeObject(new ChooseCloud());
    }

    @Override
    public void getAssistantCard() {
        clientHandler.writeObject(new GetAssistantCard());
    }

    @Override
    public void invalidAssistantCard() {
        clientHandler.writeObject(new InvalidAssistantCard());

    }
    @Override
    public void getNickname() {
        clientHandler.writeObject( new GetNickname());

    }
   @Override
    public void getPreferences() {
        clientHandler.writeObject(new GetPreferences());
    }

   @Override
   public  void letsPlay(){
        clientHandler.writeObject(new LetsPlay());
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
        clientHandler.writeObject(new InvalidNickname());
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

            clientHandler.forwardModel(modelEvent);
            //clientHandler.output.writeObject(modelEvent);

    }

    public ClientHandler getClientHandler(){
        return clientHandler;
    }

    public void notifyController(ViewEvent message){
        eventManager.notify(message);
    }
    /*@Override
    public void run() {

      /*  try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println("output stream created");
            input = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("input stream created");
        } catch (IOException e) {
            System.out.println("could not open connection to " + clientSocket.getInetAddress());
            return;
        } */

       /* try {
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
        } */

       /* try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } */

    }


   /* public void threadWait(){
       if(thisInstanceNumber != whoseTurn) {
           try {
               wait();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

    }



} */
