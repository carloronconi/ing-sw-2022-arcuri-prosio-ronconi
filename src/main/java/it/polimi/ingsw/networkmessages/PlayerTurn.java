package it.polimi.ingsw.networkmessages;

import it.polimi.ingsw.model.Player;

public class PlayerTurn {
    private final String nickname;

    public PlayerTurn(Player player){
        nickname= player.getNickname();
    }

    public String getNickname(){
        return nickname;
    }
}
