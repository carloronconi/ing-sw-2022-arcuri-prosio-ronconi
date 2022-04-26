package it.polimi.ingsw.view;

import it.polimi.ingsw.EventListener;
import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.model.ModelEvent;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Scanner;

/**
 * first implementation of CLI view
 */
public class CliView implements EventListener<ModelEvent> {
    private final EventManager<ViewGameInitializationEvent> eventManager;

    public CliView(EventManager<ViewGameInitializationEvent> eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * shows a multiple choice prompt in system out
     * @param choices among which the user chooses one
     * @param promptMessage to direct the user in the choice
     * @param viewGameInitializationEvent to notify the listeners of a certain type of view events
     */
    public void showMultipleChoicePrompt(List<String> choices, String promptMessage, ViewGameInitializationEvent viewGameInitializationEvent){
        System.out.println(promptMessage + " (Choices: " + choices + ")");
        Scanner s = new Scanner(System.in);
        String choice = s.nextLine();
        while (!choices.contains(choice)){
            System.out.println("Wrong choice, choose again:");
            choice = s.nextLine();
        }
        try {
            eventManager.notify(viewGameInitializationEvent, choice);
        } catch (InvalidObjectException e) {
            e.printStackTrace();
        }
    }

    /**
     * dhows a text input prompt in system out
     * @param promptMessage to direct the user in the choice
     * @param viewGameInitializationEvent to notify the listeners of a certain type of view events
     * @param parser lambda expression string->string to parse the user text input
     */
    public void showTextInputPrompt(String promptMessage, ViewGameInitializationEvent viewGameInitializationEvent, Parsable parser){
        System.out.println(promptMessage + ":");
        Scanner s = new Scanner(System.in);
        String text = s.nextLine();
        text = parser.parse(text);
        try {
            eventManager.notify(viewGameInitializationEvent, text);
            System.out.println("Your nickname is: "+text);
        } catch (InvalidObjectException e) {
            System.out.println("Nickname already used by other player, use a different one:");
            showTextInputPrompt(promptMessage, viewGameInitializationEvent, parser);
        }
    }

    /**
     * to draw the main view of the model containing the board and the schools
     * @param data used to draw the view
     */
    private void drawMainView(Object data){
        //TODO: should draw the main view of the model using getters to see its state
        System.out.println(data);
    }

    /**
     * called by publisher (model) to update the view
     * @param modelEvent type of model change event to react to
     * @param data relative to the event
     */
    @Override
    public void update(ModelEvent modelEvent, Object data) {
        switch (modelEvent) {
            case INITIATED_MODEL: drawMainView(data);
                break;
        }
    }
}
