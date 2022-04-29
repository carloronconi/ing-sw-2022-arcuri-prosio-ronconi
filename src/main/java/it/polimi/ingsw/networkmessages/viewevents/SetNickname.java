package it.polimi.ingsw.networkmessages.viewevents;

import java.io.Serializable;

public class SetNickname implements Serializable, ViewEvent {
    private final String nickname;

    public SetNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}