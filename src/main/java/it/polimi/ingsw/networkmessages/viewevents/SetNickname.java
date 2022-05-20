package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class SetNickname implements Serializable, SetupViewEvent {
    private final String nickname;
    private VirtualView virtualView;

    public SetNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InvalidObjectException {
        this.virtualView = virtualView;
        if (virtualView.isNicknameAlreadyUsed(nickname)) {
            virtualView.invalidNickname();
            //TODO: remove this exception since it is already treated elsewhere
            //throw new InvalidObjectException("Nickname already used");
        }
        if (virtualView.getThisInstanceNumber() == 0) {
            virtualView.getPreferences();
        } else {
            virtualView.letsPlay();
        }

    }

    public VirtualView getVirtualView() {
        return virtualView;
    }
}
