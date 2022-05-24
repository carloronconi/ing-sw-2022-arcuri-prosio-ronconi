package it.polimi.ingsw.client;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.*;

import java.util.Scanner;
import java.util.UUID;

public class CliView implements ViewInterface {
    private EventManager<ViewEvent> eventManager;
    private Scanner scanner;

    public CliView(ServerHandler handler) {
        eventManager = new EventManager<>();
        eventManager.subscribe(handler);
        scanner = new Scanner(System.in);

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
        System.out.println("Choose a Character Card:");
        boolean flag = true;
        AvailableCharacter characterEnum = null;
        while(flag){
            String characterString = scanner.nextLine();
            System.out.println("Line inserted: " + characterString);
            try{
                characterEnum = AvailableCharacter.valueOf(characterString.toUpperCase());
                flag = false;
            } catch (IllegalArgumentException e){
                System.out.println("That Character Card doesn't exist! Try again:");
            }

        }

        eventManager.notify(new ChosenCharacter(characterEnum));
    }

    @Override
    public void chooseCloud() {
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
        System.out.println("Nickname?");
        String text = scanner.nextLine();

        eventManager.notify(new SetNickname(text));
    }

    @Override
    public void getPreferences() {
        System.out.println("Easy or hard? (E/H)");
        String text = scanner.nextLine();
        while (!(text.equals("E") || text.equals("H"))){
            System.out.println("Easy or hard? (E/H)");
            text = scanner.nextLine();
        }

        GameMode gameMode = text.equals("H")? GameMode.HARD : GameMode.EASY;

        System.out.println("Number of players?");
        int numOfPlayers = scanner.nextInt();
        while (!(numOfPlayers==2|| numOfPlayers== 3)){
            System.out.println("Number of players?");
            numOfPlayers = scanner.nextInt();
        }

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
        System.out.println(modelEvent.toString());
    }
}
