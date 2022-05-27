package it.polimi.ingsw.client;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.cliview.Matrix;
import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;

import java.util.*;

public class CliView implements ViewInterface {
    private EventManager<ViewEvent> eventManager;
    private Scanner scanner;
    private GameState gameState;
    private HashMap<String, UUID> player;
    private ArrayList<Player> players;

    public CliView(ServerHandler handler) {
        eventManager = new EventManager<>();
        eventManager.subscribe(handler);
        scanner = new Scanner(System.in);

    }

    private String askUserInput(String message, InputParser parser){
        while (true){
            System.out.println(message);
            String input = scanner.nextLine();
            if (parser.isStringAccepted(input)){
                return input;
            } else {
                System.out.println("Input not accepted! Try again.");
            }
        }
    }

    @Override
    public void sendAcknowledgement() {
        System.out.println("Acknowledgement received by server");
    }

    @Override
    public void askPlayAgain() {

    }

    @Override
    public void chooseCharacter() {

        String textualCharacter = askUserInput("Choose a character card:", s->{
            try{
                AvailableCharacter c = AvailableCharacter.valueOf(s);
                return true;
            } catch (IllegalArgumentException e){
                return false;
            }
        });

        AvailableCharacter characterEnum = AvailableCharacter.valueOf(textualCharacter);

        eventManager.notify(new ChosenCharacter(characterEnum));
    }

    @Override
    public void chooseCloud() {

        //TODO: finish this and change all other methods to use askUserInput
        /*
        String cloud = askUserInput("Choose a cloud id:", s->{
            ArrayList<UUID> cloudIds = new ArrayList<>();
            for (HashMap<UUID, ArrayList<PawnColor>> c : gameState.getClouds()) {
                for (UUID id : c.keySet()){
                    cloudIds.add(id);
                }
            }
            return !cloudIds.contains(UUID.fromString(s));
        });*/

        String cloud = "";

        while(cloud.equals("")){
            System.out.println("Choose a cloud (id):");
            cloud = scanner.nextLine();
        }
        UUID uuid = UUID.fromString(cloud);

        eventManager.notify(new ChosenCloud(uuid));
    }

    @Override
    public void getAssistantCard() {
        System.out.println("Choose an Assistant Card: [1-10] ");
        int card = scanner.nextInt();
        card--;
        eventManager.notify(new SetAssistantCard(card));

    }

    @Override
    public void invalidAssistantCard() {
        System.out.println("Invalid assistant card! Try again:");
        getAssistantCard();
    }

    @Override
    public void getNickname() {
        String text = askUserInput("Nickname?", s->true);
        eventManager.notify(new SetNickname(text));
    }

    @Override
    public void getPreferences() {
        String text = askUserInput("Easy or hard? (E/H)", s-> (s.equals("E") || s.equals("H")));

        GameMode gameMode = text.equals("H")? GameMode.HARD : GameMode.EASY;

        String numString = askUserInput("Number of players?", s->(s.equals("2") || s.equals("3")));

        int numOfPlayers = Integer.valueOf(numString);

        eventManager.notify(new SetPreferences(numOfPlayers, gameMode));
    }

    @Override
    public void letsPlay(){
        System.out.println("Waiting for other player to finish setup");
        eventManager.notify(new ReadyToPlay());
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
        System.out.println("Invalid nickname! Try again:");
        getNickname();
    }

    @Override
    public void invalidStudentMove() {
        System.out.println("Invalid student move! Try again:");
        moveStudent();
    }

    @Override
    public void moveMotherNature() {
        System.out.println("How many mother nature steps?");
        int steps = scanner.nextInt();
        eventManager.notify(new MovedMotherNature(steps));
    }

    @Override
    public void moveStudent() {
        System.out.println("Choose a color of student to be moved:");
        boolean flag = true;
        PawnColor color = null;
        while(flag){
            String characterString = scanner.nextLine();
            try{
                color = PawnColor.valueOf(characterString.toUpperCase());
                flag = false;
            } catch (IllegalArgumentException e){
                System.out.println("That color doesn't exist! Try again:");
            }

        }
        System.out.println("Move the student to dining room or island? (D/I):");
        String text = scanner.nextLine();
        while (!(text.equals("D") || text.equals("I"))){
            System.out.println("Move a student to dining room or island? (D/I):");
            text = scanner.nextLine();
        }

        boolean isIsland = text.equals("I");
        UUID uuid = null;
        if(isIsland){
            System.out.println("Choose the island (id):");
            String island = scanner.nextLine();
            uuid = UUID.fromString(island);
        }

        eventManager.notify(new MovedStudent(color, uuid));
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


    public void update(ModelEvent modelEvent) {
        if(modelEvent instanceof GameState){
            gameState = (GameState) modelEvent;

            ArrayList<String> nicknames = gameState.getNicknames();

            ArrayList<Matrix> matrix = gameState.getMatrix();
            for (int i=0; i< matrix.size(); i++){
                System.out.println(nicknames.get(i).toString() + "'s school");
                matrix.get(i).dumb();
            }

            //matrix.dumb();





            //System.out.println(matrix.toString());
        }

    }





}
