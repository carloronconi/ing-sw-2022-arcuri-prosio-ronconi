package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class SetNickname implements Serializable, SetupViewEvent {
    private final String nickname;

    public SetNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {
        if (virtualView.isNicknameAlreadyUsed(nickname)) {
            virtualView.invalidNickname();
            throw new InvalidObjectException("Nickname already used");
        }
        else virtualView.
    }
}
