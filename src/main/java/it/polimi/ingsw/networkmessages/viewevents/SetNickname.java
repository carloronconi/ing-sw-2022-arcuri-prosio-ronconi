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
        } else {
            virtualView.notifyController(this);
            if (virtualView.getThisInstanceNumber() == 0) {
                virtualView.getPreferences();
            } else {
                //TODO: here wait for someone to enter the preferences, when it happened it means the controller
                // knows whose turn it is, so send virtualView.whoseTurn() and wait again
                // until it is this player's turn, then send virtualView.getAssistantCard()
                virtualView.letsPlay();
            }
        }

    }

    public VirtualView getVirtualView() {
        return virtualView;
    }
}
