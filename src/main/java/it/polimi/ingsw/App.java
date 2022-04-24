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
        EventManager<ControllerEventType> cliEventManager = new EventManager<>(ControllerEventType.class);
        CliView cliView = new CliView(cliEventManager);
        GameController gameController = new GameController(cliView);
        //for now game controller subscribes for all types of event
        for (ControllerEventType e : ControllerEventType.values()){
            cliEventManager.subscribe(e,gameController);
        }


        gameController.startSetup();
    }
}
