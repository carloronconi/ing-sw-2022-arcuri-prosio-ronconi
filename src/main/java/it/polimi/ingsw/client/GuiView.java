package it.polimi.ingsw.client;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;
import javafx.application.Platform;
import javafx.scene.image.Image;

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
    private String winner;
    private CliViewIdConverter initialStateConverter;

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
        changeSceneToGameBoard();
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
            }
        }));

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
        changeSceneToGameBoard();
    }

    @Override
    public void moveStudent() {
        changeSceneToGameBoard();

    }

    @Override
    public void gameOver(UUID winner) {
        CliViewIdConverter converter = new CliViewIdConverter(gameState);
        this.winner = converter.idToName(winner, CliViewIdConverter.converterSetting.PLAYER);
        Platform.runLater(new ChangeScene("/GameOver.fxml", clientGui));
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

    private void changeSceneToGameBoard(){
        String name = gameState.getNicknames().size()==2? "/GameBoard2.fxml" : "/GameBoard3.1.fxml";
        Platform.runLater(new ChangeScene(name, clientGui, (s, c)->{
            if (c instanceof GameBoardController){
                GameBoardController boardController = (GameBoardController) c;
                boardController.updateBoard(/*getNextGameState(gameState.getId())*/gameState);
            }
        }));
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
        if (initialStateConverter==null) initialStateConverter = new CliViewIdConverter(gameState);

        System.out.println("notified waiters of this gameState:" + gameState);
        //UUID[] uuids = new UUID[2];
        //System.out.println(gameState.drawGameState(gameState.getNicknames().keySet().stream().findFirst().get(), new ArrayList<>(List.of(gameState.getIslands().keySet().toArray(uuids)))));
        notifyAll();
    }
}
