package it.polimi.ingsw.view;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.EventType;

import java.util.List;
import java.util.Scanner;

public class CliView {
    private final EventManager eventManager;

    public CliView(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void showMultipleChoicePrompt(List<String> choices, String promptMessage, EventType eventType){
        System.out.println(promptMessage + "(Choices: " + choices + ")");
        Scanner s = new Scanner(System.in);
        String choice = s.nextLine();
        while (!choices.contains(choice)){
            System.out.println("Wrong choice, choose again:");
            choice = s.nextLine();
        }
        eventManager.notify(eventType, choice);
    }

    public void showTextInputPrompt(String promptMessage, EventType eventType){
        System.out.println(promptMessage + ":");
        Scanner s = new Scanner(System.in);
        String text = s.nextLine();
        eventManager.notify(eventType, text);
    }
}
