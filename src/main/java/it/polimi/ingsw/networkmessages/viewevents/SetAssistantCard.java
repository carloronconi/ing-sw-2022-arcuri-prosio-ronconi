package it.polimi.ingsw.networkmessages.viewevents;

import java.io.Serializable;

public class SetAssistantCard implements Serializable, GameViewEvent {
    private final Integer numCard;

    public SetAssistantCard(Integer numCard){
        this.numCard=numCard;
    }

    public Integer getCard(){
        return numCard;
    }





}
