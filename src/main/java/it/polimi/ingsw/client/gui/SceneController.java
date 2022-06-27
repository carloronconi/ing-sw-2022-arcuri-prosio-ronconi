package it.polimi.ingsw.client.gui;

public class SceneController {
    private ClientGui clientGui;
    public void setClientGui(ClientGui clientGui) {
        this.clientGui = clientGui;
    }

    protected ClientGui getClientGui() {
        return clientGui;
    }
}
