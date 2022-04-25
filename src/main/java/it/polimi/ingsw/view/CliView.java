package it.polimi.ingsw.view;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.model.ModelEventType;

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
        eventManager.notify(viewEventType, choice);
    }

    public void showTextInputPrompt(String promptMessage, ViewEventType viewEventType){
        System.out.println(promptMessage + ":");
        Scanner s = new Scanner(System.in);
        String text = s.nextLine();
        eventManager.notify(viewEventType, text);
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
