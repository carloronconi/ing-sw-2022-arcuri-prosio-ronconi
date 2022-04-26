package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.ModelEvent;
import it.polimi.ingsw.view.CliView;
import it.polimi.ingsw.view.ViewGameInitializationEvent;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EventManager<ViewGameInitializationEvent> viewEventManager = new EventManager<>(ViewGameInitializationEvent.class);
        CliView cliView = new CliView(viewEventManager);
        GameController gameController = new GameController(cliView);
        //for now game controller subscribes for all types of event
        for (ViewGameInitializationEvent e : ViewGameInitializationEvent.values()){
            viewEventManager.subscribe(e,gameController);
        }
        EventManager<ModelEvent> modelEventManager = new EventManager<>(ModelEvent.class);
        for (ModelEvent e : ModelEvent.values()){
            modelEventManager.subscribe(e,cliView);
        }
        gameController.startGame(modelEventManager);
    }
}
