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
        EventManager<ViewEventType> cliEventManager = new EventManager<>(ViewEventType.class);
        CliView cliView = new CliView(cliEventManager);
        GameController gameController = new GameController(cliView);
        //for now game controller subscribes for all types of event
        for (ViewEventType e : ViewEventType.values()){
            cliEventManager.subscribe(e,gameController);
        }


        gameController.startSetup();
    }
}
