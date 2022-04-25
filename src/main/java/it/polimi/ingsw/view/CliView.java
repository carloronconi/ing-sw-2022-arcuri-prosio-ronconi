package it.polimi.ingsw.view;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.model.ModelEventType;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Scanner;

/**
 * first implementation of CLI view
 */
public class CliView implements EventListener<ModelEventType> {
    private final EventManager<ViewEventType> eventManager;

    public CliView(EventManager<ViewEventType> eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * shows a multiple choice prompt in system out
     * @param choices among which the user chooses one
     * @param promptMessage to direct the user in the choice
     * @param viewEventType to notify the listeners of a certain type of view events
     */
    public void showMultipleChoicePrompt(List<String> choices, String promptMessage, ViewEventType viewEventType){
        System.out.println(promptMessage + " (Choices: " + choices + ")");
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

    /**
     * dhows a text input prompt in system out
     * @param promptMessage to direct the user in the choice
     * @param viewEventType to notify the listeners of a certain type of view events
     * @param parser lambda expression string->string to parse the user text input
     */
    public void showTextInputPrompt(String promptMessage, ViewEventType viewEventType, Parsable parser){
        System.out.println(promptMessage + ":");
        Scanner s = new Scanner(System.in);
        String text = s.nextLine();
        text = parser.parse(text);
        try {
            eventManager.notify(viewEventType, text);
            System.out.println("Your nickname is: "+text);
        } catch (InvalidObjectException e) {
            System.out.println("Nickname already used by other player, use a different one:");
            showTextInputPrompt(promptMessage, viewEventType, parser);
        }
    }

    /**
     * to draw the main view of the model containing the board and the schools
     * @param data used to draw the view
     */
    private void drawMainView(String data){
        //TODO: should draw the main view of the model using getters to see its state
        System.out.println(data);
    }

    /**
     * called by publishers to update the view
     * @param modelEventType type of model change event to react to
     * @param data relative to the event
     */
    @Override
    public void update(ModelEventType modelEventType, String data) {
        switch (modelEventType) {
            case INITIATED_MODEL: drawMainView(data);
                break;
        }
    }
}
