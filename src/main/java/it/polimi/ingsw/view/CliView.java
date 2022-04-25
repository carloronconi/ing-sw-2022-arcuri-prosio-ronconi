package it.polimi.ingsw.view;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.model.ModelEventType;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Scanner;

public class CliView implements EventListener<ModelEventType> {
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
        try {
            eventManager.notify(viewEventType, choice);
        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
    }

    public void showTextInputPrompt(String promptMessage, ViewEventType viewEventType){
        System.out.println(promptMessage + ":");
        Scanner s = new Scanner(System.in);
        String text = s.nextLine();
        try {
            eventManager.notify(viewEventType, text);
        } catch (InvalidObjectException e) {
            System.out.println("Nickname already used by other player, use a different one:");
            showTextInputPrompt(promptMessage, viewEventType);
        }
    }

    private void drawMainView(String data){
        //TODO: should draw the main view of the model using getters to see its state
        System.out.println(data);
    }

    @Override
    public void update(ModelEventType modelEventType, String data) {
        switch (modelEventType) {
            case INITIATED_MODEL: drawMainView(data);
                break;
        }
    }
}
