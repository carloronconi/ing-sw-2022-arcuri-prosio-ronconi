package it.polimi.ingsw.view;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewEventType;

import java.util.List;
import java.util.Scanner;

public class CliView {
    private final EventManager<ViewEventType> eventManager;

    public CliView(EventManager<ViewEventType> eventManager) {
        this.eventManager = eventManager;
    }

    public void showMultipleChoicePrompt(List<String> choices, String promptMessage, ViewEventType viewEventType){
        System.out.println(promptMessage + "(Choices: " + choices + ")");
        Scanner s = new Scanner(System.in);
        String choice = s.nextLine();
        while (!choices.contains(choice)){
            System.out.println("Wrong choice, choose again:");
            choice = s.nextLine();
        }
        eventManager.notify(viewEventType, choice);
    }

    public void showTextInputPrompt(String promptMessage, ViewEventType viewEventType){
        System.out.println(promptMessage + ":");
        Scanner s = new Scanner(System.in);
        String text = s.nextLine();
        eventManager.notify(viewEventType, text);
    }
}
