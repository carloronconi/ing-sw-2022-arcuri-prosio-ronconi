package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.controller.GameMode;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SetPreferences implements Serializable, SetupViewEvent {
    private final int numOfPlayers;
    private final GameMode gameMode;
    // private List<UUID> players;
   // private TurnController turnController;

    public SetPreferences(int numOfPlayers, GameMode gameMode) {
        this.numOfPlayers = numOfPlayers;
        this.gameMode = gameMode;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public void processMessage(VirtualView virtualView) throws InterruptedException {

         virtualView.letsPlay();



        }

        //TODO: is my turn e thread block goes in controller
       /* for(VirtualView v : views){
            if(!virtualView.isItMyTurn()){
                try{
                    wait();}catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else{
                virtualView.getAssistantCard();
            }
        } */



       /* for(int i=0; i<numOfPlayers; i++){
            if(!virtualView.isItMyTurn()){
                try{
                wait();}catch (InterruptedException e){
                    e.printStackTrace();
                }
            }else{
                virtualView.getAssistantCard();
            }
            i++;
        } */
    /*  while (!virtualView.isItMyTurn()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        virtualView.getAssistantCard();
*/


        //TODO: I think that problem is here - maybe there should be another message between set preferences and get assistant
        //TODO: card to let the virtual view process the new order of players
       //for(VirtualView v: views) virtualView.letsPlay();

    }
