package it.polimi.ingsw.networkmessages;

import java.io.Serializable;

public class SetNickname implements Serializable {
    private final String nickname;

    public SetNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
