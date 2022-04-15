package it.polimi.ingsw.model;

import it.polimi.ingsw.model.studentmanagers.DiningRoom;
import it.polimi.ingsw.model.studentmanagers.Entrance;

import java.util.ArrayList;
import java.util.UUID;

public class Player {
    /*se metto final per Entrance e DiningRoom non posso usare il costruttore Player() in GameModel perché altrimenti
    non potrei ppiù aggiungere player dopo ma dovrei inserirli direttamente quando chiamo il costruttore di GameModel
    all'interno del quale dovrei chiamare il costruttore Player(Entrance, DiningRoom)*/
    private Entrance entrance;
    private DiningRoom diningRoom;
    private UUID id;
    private String nickName;
    //private Wizard wizard;
    private TowerColor towerColor;
    private ArrayList<Integer> assistantDeck;
    private int numOfCoins;
    private int coins;


    //ho defintio questo costruttore perché altrimenti in checkNewOwner non avrei potuto definire
    //la variabile supportPlayer d'appoggio. è giusto o sbagliato? se non l'avessi definito ci sarebbe stato un
    //costruttore di default oppure no perché già definito quello per questa classe?
    public Player(){
        this.id=UUID.randomUUID();
    }

    public Player(Entrance entrance, DiningRoom diningRoom) {
        this.entrance = entrance;
        this.diningRoom = diningRoom;
        this.id=UUID.randomUUID();
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


    /* da capire se questo metodo bisogna implementarlo in Player o in GameModel
    public int getNumOfTowers(){

    }
    */







}
