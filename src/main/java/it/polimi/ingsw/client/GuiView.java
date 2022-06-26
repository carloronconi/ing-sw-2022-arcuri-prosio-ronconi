package it.polimi.ingsw.client;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class GuiView implements ViewInterface {
    private EventManager<ViewEvent> eventManager;
    private Scanner scanner;
    private final ClientGui clientGui;
    private GameState gameState;
    private boolean keepOldView;
    private String winner = null;
    private CliViewIdConverter initialStateConverter;
    private boolean invalidBoardMove;
    private AtomicBoolean isWaitingGameBoard = new AtomicBoolean(false);

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

    public boolean isInvalidBoardMove() {
        boolean oldValue = invalidBoardMove;
        invalidBoardMove = false;
        return oldValue;
    }

    public GameState getGameState() {
        return gameState;
    }

    public String getWinner() {
        return winner;
    }
    public CliViewIdConverter getInitialStateConverter() {
        return initialStateConverter;
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
        Platform.runLater(new ChangeScene("/SetCharacterCard.fxml", clientGui, (s, c)->{
            if( c instanceof SetCharacterCardSceneController){
                SetCharacterCardSceneController setCharacterCardSceneController = (SetCharacterCardSceneController) c;
                setCharacterCardSceneController.initializeCards(clientGui.getGuiView().getGameState());
                System.out.println("CHARACTER");
            }
        }));
    }

    @Override
    public void chooseCloud() {
        changeSceneToGameBoard(true, false);
    }

    @Override
    public void getAssistantCard() {
        String name = gameState.getNicknames().size()==2? "/ChooseAssistantCard.fxml" : "/ChooseAssistantCard3.fxml";
        Platform.runLater(new ChangeScene(name, clientGui, (s, c)->{
            if (c instanceof SetAssistantSceneController){
                SetAssistantSceneController controller = (SetAssistantSceneController) c;
                ArrayList<String> resources = clientGui.getPlayedByOtherResources();
                if (!resources.isEmpty()){
                    controller.getPlayedByOther().setImage(new Image(String.valueOf(getClass().getResource(resources.get(0)))));
                    if (controller.getPlayedBySecondOther()!=null && resources.size()>1) controller.getPlayedBySecondOther().setImage(new Image(String.valueOf(getClass().getResource(resources.get(1)))));
                }
                controller.label1.setText(clientGui.getFinalNickname());
                //TODO: (nice to have) set other labels to contain the right nickname
                controller.label2.setText("opponent");
                if (controller.label3!=null) controller.label3.setText("opponent");

                ArrayList<Integer> assistantDeck = gameState.getAssistantDecks().get(new CliViewIdConverter(gameState).nameToId(clientGui.getFinalNickname(), CliViewIdConverter.ConverterSetting.PLAYER));
                System.out.println("assistant deck: "+assistantDeck);
                if (!assistantDeck.contains(1)) controller.myCards.getChildren().remove(controller.card1);
                if (!assistantDeck.contains(2)) controller.myCards.getChildren().remove(controller.card2);
                if (!assistantDeck.contains(3)) controller.myCards.getChildren().remove(controller.card3);
                if (!assistantDeck.contains(4)) controller.myCards.getChildren().remove(controller.card4);
                if (!assistantDeck.contains(5)) controller.myCards.getChildren().remove(controller.card5);
                if (!assistantDeck.contains(6)) controller.myCards.getChildren().remove(controller.card6);
                if (!assistantDeck.contains(7)) controller.myCards.getChildren().remove(controller.card7);
                if (!assistantDeck.contains(8)) controller.myCards.getChildren().remove(controller.card8);
                if (!assistantDeck.contains(9)) controller.myCards.getChildren().remove(controller.card9);
                if (!assistantDeck.contains(10)) controller.myCards.getChildren().remove(controller.card10);


            }
        }));
        isWaitingGameBoard.set(false);
    }

    @Override
    public void invalidAssistantCard() {
        System.out.println("Invalid assistant card");
        getAssistantCard();
    }

    @Override
    public void getNickname(){
        Platform.runLater(new ChangeScene("/SetNickname.fxml", clientGui, 800, 530));
    }

    @Override
    public void getPreferences() {
        Platform.runLater(new ChangeScene("/SetPreferences.fxml", clientGui, 800, 530));
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
        invalidBoardMove = true;
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
        invalidBoardMove = true;
        System.out.println("invalid student move");
        moveStudent();
    }

    @Override
    public void moveMotherNature() {
        changeSceneToGameBoard(true, false);
    }

    @Override
    public void moveStudent() {
        changeSceneToGameBoard(true, false);

    }

    @Override
    public void gameOver(UUID winner) {
        CliViewIdConverter converter = new CliViewIdConverter(gameState);
        if (winner!=null) this.winner = converter.idToName(winner, CliViewIdConverter.ConverterSetting.PLAYER);
        Platform.runLater(new ChangeScene("/GameOver.fxml", clientGui, (s, c)->{
            if (c instanceof GameOverSceneController) ((GameOverSceneController) c).setup();
        }));
    }

    @Override
    public void getCharacterSettings(AvailableCharacter forCharacter) {
        Platform.runLater(new ChangeScene("/CharacterSettings.fxml", clientGui, (s, c)->{
            if( c instanceof SetCharacterSettingsController){
                SetCharacterSettingsController setCharacterSettingsController = (SetCharacterSettingsController) c;
                setCharacterSettingsController.initializeSettings(clientGui.getGuiView().getGameState(), forCharacter);
                System.out.println("CHARACTER SETTINGS");
            }
        }));
    }

    public void changeSceneToGameBoard(boolean runLater, boolean makeTransparent){
        String name = gameState.getNicknames().size()==2? "/GameBoard2.fxml" : "/GameBoard3.1.fxml";
        SceneInitializer initializer = (scene, controller) -> {
            if (controller instanceof GameBoardController){
                GameBoardController boardController = (GameBoardController) controller;
                boardController.updateBoard(gameState);
                if (makeTransparent) boardController.vbox.setMouseTransparent(true);
            }
        };
        if (runLater){
            Platform.runLater(new ChangeScene(name, clientGui, initializer));
            //isWaitingGameBoard.set(false);
        } else {
            new ChangeScene(name, clientGui, initializer).run();
            //isWaitingGameBoard.set(true);
        }
        isWaitingGameBoard.set(makeTransparent);
    }

    /*
    private void setNextSceneNameGameBoard(){
        String name = gameState.getNicknames().size()==2? "/GameBoard2.fxml" : "/GameBoard3.1.fxml";
        clientGui.setNextSceneName(name);
    }

    private void setNextSceneNameAssistant(){
        String name = gameState.getNicknames().size()==2? "/ChooseAssistantCard.fxml" : "/ChooseAssistantCard3.fxml";
        System.out.println("setting next scene name to assistantCArd");
        clientGui.setNextSceneName(name);
    }*/

    public void notifyEventManager(ViewEvent event){
        eventManager.notify(event);
    }

    @Override
    public synchronized void update(ModelEvent modelEvent) {
        gameState = (GameState) modelEvent;
        if (isWaitingGameBoard.get()) changeSceneToGameBoard(true, true);
        if (initialStateConverter==null) initialStateConverter = new CliViewIdConverter(gameState);

        //System.out.println("notified waiters of this gameState:" + gameState);
        //UUID[] uuids = new UUID[2];
        //System.out.println(gameState.drawGameState(gameState.getNicknames().keySet().stream().findFirst().get(), new ArrayList<>(List.of(gameState.getIslands().keySet().toArray(uuids)))));
        notifyAll();
    }
}
