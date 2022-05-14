package it.polimi.ingsw.client;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;
import it.polimi.ingsw.networkmessages.viewevents.SetAssistantCard;
import it.polimi.ingsw.networkmessages.viewevents.SetNickname;
import it.polimi.ingsw.networkmessages.viewevents.SetPreferences;
import it.polimi.ingsw.networkmessages.viewevents.ViewEvent;

import java.util.Scanner;

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

    }

    @Override
    public void chooseCloud() {

    }

    @Override
    public void getAssistantCard() {
        System.out.println("Choose an Assistant Card: [1-10] ");
        int card = scanner.nextInt();
        eventManager.notify(new SetAssistantCard(card));

    }

    @Override
    public void invalidAssistantCard() {

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
        getNickname();
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
        System.out.println(modelEvent.toString());
    }
}
