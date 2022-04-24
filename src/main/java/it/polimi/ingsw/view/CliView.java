package it.polimi.ingsw.view;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ControllerEventType;

import java.util.List;
import java.util.Scanner;

public class CliView {
    private final EventManager<ControllerEventType> eventManager;

    public CliView(EventManager<ControllerEventType> eventManager) {
        this.eventManager = eventManager;
    }

    public void showMultipleChoicePrompt(List<String> choices, String promptMessage, ControllerEventType controllerEventType){
        System.out.println(promptMessage + "(Choices: " + choices + ")");
        Scanner s = new Scanner(System.in);
        String choice = s.nextLine();
        while (!choices.contains(choice)){
            System.out.println("Wrong choice, choose again:");
            choice = s.nextLine();
        }
        eventManager.notify(controllerEventType, choice);
    }

    public void showTextInputPrompt(String promptMessage, ControllerEventType controllerEventType){
        System.out.println(promptMessage + ":");
        Scanner s = new Scanner(System.in);
        String text = s.nextLine();
        eventManager.notify(controllerEventType, text);
    }
}
