package it.polimi.ingsw.networkmessages.controllercalls;

import it.polimi.ingsw.server.VirtualView;


public interface RemoteMethodCall {
    /**
     * process the message using the real view of the client that received the message
     */
    void processMessage();
}
