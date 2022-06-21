package it.polimi.ingsw.client;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class GuiView implements ViewInterface {
    private EventManager<ViewEvent> eventManager;
    private Scanner scanner;
    private final ClientGui clientGui;
    private GameState gameState;
    private boolean keepOldView;

    //private JTextField introductionField;
    //private JTextArea screenArea;
    // private String message = "";
    // private String serverChat;
    public GuiView(ServerHandler serverHandler, ClientGui clientGui){
        eventManager = new EventManager<>();
        eventManager.subscribe(serverHandler);
        scanner = new Scanner(System.in);

        this.clientGui = clientGui;

        //introductionField = new JTextField();
        // introductionField.setEditable( false );

    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * waits for a new gameState to arrive before returning the gameState
     * @return updated version of gameState as soon as a gameState different from currentState arrives
     * @param currentState used to compare the new one against
     */
    public synchronized GameState getNextGameState(UUID currentState){
        //int i = 0;
        System.out.println("called getNextGameState");
        while (currentState.equals(gameState.getId()) && !keepOldView/*i<2*/){
            System.out.println("Game states are the same");
            //i++;
            try {
                wait(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        keepOldView = false;
        System.out.println("returning next gameState");
        return gameState;
    }

    @Override
    public void sendAcknowledgement() {
        System.out.println("Acknowledgement received by server on guiView");
    }

    @Override
    public void askPlayAgain() {

    }

    @Override
    public void chooseCharacter() {
        clientGui.setNextSceneName("/GameBoard2.fxml");
    }

    @Override
    public void chooseCloud() {
        clientGui.setNextSceneName("/GameBoard2.fxml");
    }

    @Override
    public void getAssistantCard() {
        clientGui.setNextSceneName("/ChooseAssistantCard.fxml");

    }

    @Override
    public void invalidAssistantCard() {
        System.out.println("Invalid assistant card");
        getAssistantCard();
    }

    @Override
    public void getNickname(){
        clientGui.setNextSceneName("/SetNickname.fxml");
    }

    @Override
    public void getPreferences() {
        clientGui.setNextSceneName("/SetPreferences.fxml");
    }

    @Override
    public void letsPlay() {
        System.out.println("Waiting for other players...");
        notifyEventManager(new ReadyToPlay());
    }

    @Override
    public void playerTurn() {

    }

    @Override
    public synchronized void invalidCharacterChoice() {
        keepOldView = true;
        System.out.println("invalid character choice");
        chooseCharacter();
    }

    @Override
    public synchronized void invalidMNMove() {
        keepOldView = true;
        System.out.println("invalid mother nature move");
        moveMotherNature();
    }

    @Override
    public void invalidNickname() {
        System.out.println("invalid nickname");
        getNickname();
    }

    @Override
    public synchronized void invalidStudentMove() {
        keepOldView = true;
        System.out.println("invalid student move");
        moveStudent();
    }

    @Override
    public void moveMotherNature() {
        clientGui.setNextSceneName("/GameBoard2.fxml");
    }

    @Override
    public void moveStudent() {
        clientGui.setNextSceneName("/GameBoard2.fxml");
    }

    @Override
    public void gameOver(UUID winner) {

    }

    @Override
    public void getCharacterSettings(AvailableCharacter forCharacter) {

    }

    public void notifyEventManager(ViewEvent event){
        eventManager.notify(event);
    }

    @Override
    public synchronized void update(ModelEvent modelEvent) {
        gameState = (GameState) modelEvent;
        System.out.println("notified waiters of this gameState:" + gameState);
        UUID[] uuids = new UUID[2];
        System.out.println(gameState.drawGameState(gameState.getNicknames().keySet().stream().findFirst().get(), new ArrayList<>(List.of(gameState.getIslands().keySet().toArray(uuids)))));
        notifyAll();
    }
}
