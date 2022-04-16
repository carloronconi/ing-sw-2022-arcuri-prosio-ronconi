package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;

import java.util.ArrayList;
import java.util.UUID;

public class Player {
    private final Entrance entrance;
    private final DiningRoom diningRoom;
    private final UUID id;
    private String nickName;
    //TODO private Wizard wizard;
    private TowerColor towerColor;
    private ArrayList<Integer> assistantDeck;
    private int numOfCoins;
    private int coins;


    public Player(Entrance entrance, DiningRoom diningRoom) {
        this.entrance = entrance;
        this.diningRoom = diningRoom;
        id=UUID.randomUUID();
        coins=0;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }

    public UUID getId(){ return id; }

    public int getDeckSize(){
        return assistantDeck.size();
    }

    /**
     * this method removes from the deck of available cards the card chosen by the player
     * and played in planning stages
     * @param cardNumber is the number of the card to be removed
     */
    public void playAssistantCard(int cardNumber){

        assistantDeck.remove(cardNumber);
    }

    /**
     * this method moves a student from the entrance to the dining room on a player's board.
     * If the value of the boolean is true and if the number of students of the color passed in is
     * a multiple of 3, then it increases the coins owned by a player by one.
     * @param pawnColor is the student's color that needs to be moved from the entrance to the diningRoom
     * @param moneyBank indicates whether the general bank has coins that can be withdrawn
     * @return true if the amount of coins owned by a player increases, otherwise it returns false
     */
    public boolean moveStudentToDining(PawnColor pawnColor, boolean moneyBank){

        diningRoom.fill(pawnColor);

        if(moneyBank && diningRoom.count(pawnColor)%3==0){
            coins++;
            return true;
        }

        return false;
    }




}
