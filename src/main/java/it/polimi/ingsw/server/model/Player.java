package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.server.model.studentmanagers.Entrance;

import java.util.ArrayList;
import java.util.UUID;

public class Player implements Identifiable{
    private final Entrance entrance;
    private final DiningRoom diningRoom;
    private final UUID id;
    private final String nickname;
    //TODO private Wizard wizard;
    private TowerColor towerColor;
    private ArrayList<Integer> assistantDeck;
    private int coins;
    private static int numberOfInstances = 0;
    private final int thisInstanceNumber;


    public Player(Entrance entrance, DiningRoom diningRoom, String nickname) {
        this.entrance = entrance;
        this.diningRoom = diningRoom;
        this.nickname = nickname;
        id=UUID.randomUUID();
        coins=0;
        //TODO: remove this cheat code
        if(nickname.equals("MONEY")) coins = 100;

        assistantDeck = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            assistantDeck.add(i);
        }
        thisInstanceNumber = numberOfInstances;
        if (thisInstanceNumber==0){
            towerColor=TowerColor.WHITE;
        }else if (thisInstanceNumber==1){
            towerColor=TowerColor.BLACK;
        }else{
            towerColor=TowerColor.GREY;
        }
        numberOfInstances++;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }

    public UUID getId(){ return id; }

    public String getNickname() {
        return nickname;
    }

    public int getDeckSize(){
        return assistantDeck.size();
    }

    public ArrayList<Integer> getDeck(){
        return (ArrayList<Integer>) assistantDeck.clone();
    }

    public TowerColor getTowerColor(){
        return towerColor;
    }

    /**
     * this method removes from the deck of available cards the card chosen by the player
     * and played in planning stages
     * @param cardNumber is the number of the card to be removed
     */
    public void playAssistantCard(int cardNumber){
        int index = assistantDeck.indexOf(cardNumber);
        assistantDeck.remove(index);
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

    /**
     * get the number of coins of a player
     * @return number of coins owned by player
     */
    public int getNumOfCoins() {
        return coins;
    }

    /**
     * reduces number of coins owned by the player
     * @param howMany coins to be paid
     */
    public void payCoins(int howMany){
        coins -= howMany;
    }

    @Override
    public String toString() {
        String s = nickname + "'s school:\n    " +
                "Entrance: " + entrance + "\n    " +
                "Dining room: " + diningRoom + "\n    "+
                "Tower color:" + "empty\n    " +
                "Assistant deck: " + assistantDeck + "\n    "+
                "Coins: " + coins;
        return s;
    }
}