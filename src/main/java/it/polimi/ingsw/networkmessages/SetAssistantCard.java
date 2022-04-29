package it.polimi.ingsw.networkmessages;

import java.io.Serializable;

public class SetAssistantCard implements Serializable, ViewEvent {
    private final Integer numCard;

    public SetAssistantCard(Integer numCard){
        this.numCard=numCard;
    }

    public Integer getCard(){
        return numCard;
    }





}
