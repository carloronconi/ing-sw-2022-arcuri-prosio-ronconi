package it.polimi.ingsw.networkmessages;

import java.io.Serializable;

public enum RemoteMethodCall implements Serializable {
    ACKNOWLEDGEMENT,
    ASK_PLAY_AGAIN,
    CHOOSE_CHARACTER,
    CHOOSE_CLOUD,
    GET_ASSISTANT_CARD,
    GET_NICKNAME,
    GET_PREFERENCES,
    INVALID_CHARACTER_CHOICE,
    INVALID_MN_MOVE,
    INVALID_NICKNAME,
    MOVE_MOTHER_NATURE,
    MOVE_STUDENT
}
