package it.polimi.ingsw.controller;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.EventType;
import it.polimi.ingsw.view.CliView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        EventManager eventManager = new EventManager();
        CliView cliView = new CliView(eventManager);
        gameController = new GameController(cliView);
        //for now game controller subscribes for all types of event
        for (EventType e : EventType.values()){
            eventManager.subscribe(e,gameController);
        }

    }

    @Test
    void startSetup() {
        gameController.startSetup();
        //OutputStream outputStream = new DataOutputStream();
        //System.setIn(outputStream);
    }
}