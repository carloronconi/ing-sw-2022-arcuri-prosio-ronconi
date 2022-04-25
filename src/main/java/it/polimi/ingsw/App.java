package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.ModelEventType;
import it.polimi.ingsw.view.CliView;
import it.polimi.ingsw.view.ViewEventType;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EventManager<ViewEventType> viewEventManager = new EventManager<>(ViewEventType.class);
        CliView cliView = new CliView(viewEventManager);
        GameController gameController = new GameController(cliView);
        //for now game controller subscribes for all types of event
        for (ViewEventType e : ViewEventType.values()){
            viewEventManager.subscribe(e,gameController);
        }
        EventManager<ModelEventType> modelEventManager = new EventManager<>(ModelEventType.class);
        for (ModelEventType e : ModelEventType.values()){
            modelEventManager.subscribe(e,cliView);
        }
        gameController.startGame(modelEventManager);
    }
}
