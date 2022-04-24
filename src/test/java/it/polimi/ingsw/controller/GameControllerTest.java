package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ControllerEventType;
import it.polimi.ingsw.view.CliView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        EventManager<ControllerEventType> cliEventManager = new EventManager<>(ControllerEventType.class);
        CliView cliView = new CliView(cliEventManager);
        gameController = new GameController(cliView);
        //for now game controller subscribes for all types of event
        for (ControllerEventType e : ControllerEventType.values()){
            cliEventManager.subscribe(e,gameController);
        }

    }

    @Test
    void startSetup() {
        gameController.startSetup();
        //OutputStream outputStream = new DataOutputStream();
        //System.setIn(outputStream);
    }
}