package it.polimi.ingsw.networkmessages.controllercalls;

import it.polimi.ingsw.model.Player;

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
    public void processMessage() {

    }
}
