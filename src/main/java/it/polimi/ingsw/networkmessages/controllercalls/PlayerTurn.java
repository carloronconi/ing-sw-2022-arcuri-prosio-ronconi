package it.polimi.ingsw.networkmessages.controllercalls;

import it.polimi.ingsw.EventManager;
import it.polimi.ingsw.ViewInterface;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.networkmessages.modelevents.ModelEvent;

import java.io.Serializable;

public class PlayerTurn implements Serializable, RemoteMethodCall {
    private final String nickname;

    public PlayerTurn(Player player){
        nickname= player.getNickname();
    }

    public String getNickname(){
        return nickname;
    }

    @Override
    public void processMessage(ViewInterface viewInterface, EventManager<ModelEvent> eventManager) {
        viewInterface.playerTurn();
    }
}
