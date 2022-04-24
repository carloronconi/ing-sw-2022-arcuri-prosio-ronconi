package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.view.CliView;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EventManager eventManager = new EventManager();
        CliView cliView = new CliView(eventManager);
        GameController gameController = new GameController(cliView);
        //for now game controller subscribes for all types of event
        for (EventType e : EventType.values()){
            eventManager.subscribe(e,gameController);
        }

        gameController.startSetup();
    }
}
