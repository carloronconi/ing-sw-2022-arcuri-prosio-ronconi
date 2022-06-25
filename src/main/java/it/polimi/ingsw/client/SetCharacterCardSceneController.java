package it.polimi.ingsw.client;

import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.zip.CheckedInputStream;

public class SetCharacterCardSceneController extends SceneController {
    @FXML Label card1;
    @FXML Label card2;
    @FXML Label card3;

    @FXML ImageView card1m;
    @FXML ImageView card2m;
    @FXML ImageView card3m;

    @FXML Label money1;
    @FXML Label money2;
    @FXML Label money3;

    @FXML Label player1;
    @FXML Label player2;
    @FXML Label player3;

    @FXML  Label increasedCostCard1;
    @FXML  Label increasedCostCard2;
    @FXML  Label increasedCostCard3;

    private final ArrayList<String> characterArrayList = new ArrayList<>();
    private final ArrayList<Label> cardLab = new ArrayList<>();
    private final ArrayList<ImageView> cardImage = new ArrayList<>();



    public void initialize(){
        cardLab.add(card1);
        cardLab.add(card2);
        cardLab.add(card3);

        cardImage.add(card1m);
        cardImage.add(card2m);
        cardImage.add(card3m);


    }

    public void initializeCards(GameState gameState)  {

        //cards
        HashMap<AvailableCharacter, Boolean> availableCharacterMap = gameState.getCharacterCards();
        System.out.println("tutto ok!!!");
        for (int i = 0; i < availableCharacterMap.size(); i++) {
            AvailableCharacter av = (AvailableCharacter) availableCharacterMap.keySet().toArray()[i];
            System.out.println(av.name());

            String characterName = av.name().toLowerCase(Locale.ROOT);
            characterArrayList.add(characterName);
            System.out.println(characterName);
            for (int j = 0; j < cardLab.size(); j++) {
                if(i==j){
                    cardLab.get(i).setText(characterName);
                }
            }

             for (int j = 0; j < cardImage.size(); j++) {
                if(i==j){
                    String pathName = "/" + characterName + ".jpg";
                    cardImage.get(i).setImage(new Image(String.valueOf(getClass().getResource(pathName))));
                }
            }


        }

        //players
        //coins map
        HashMap<UUID, Integer> coinsHashMap = gameState.getCoinsMap();
        //players map
        HashMap<UUID, String> playersMap = gameState.getNicknames();

        for(UUID id : coinsHashMap.keySet()){
                    if(id == playersMap.keySet().toArray()[0]){
                        player1.setText(playersMap.get(id));
                        money1.setText("YOUR MONEY: " + coinsHashMap.get(id));
                    }else if(id == playersMap.keySet().toArray()[1]){
                player2.setText(playersMap.get(id));
                money2.setText("YOUR MONEY: " + coinsHashMap.get(id));
            }else if(id == playersMap.keySet().toArray()[3]){
                        player3.setText(playersMap.get(id));
                        money3.setText("YOUR MONEY: " + coinsHashMap.get(id));
                    }

            }

        }





       public void clickedCard1(){
            card1m.setOpacity(0.4d);
            System.out.println(characterArrayList.get(0));
            increasedCostCard1.setText("COST +1 = TRUE");

                }
        public void clickedCard2(){
            card2m.setOpacity(0.4d);
            System.out.println(characterArrayList.get(1));
            increasedCostCard2.setText("COST +1 = TRUE");
        }

         public void clickedCard3(){
             card3m.setOpacity(0.4d);
             System.out.println(characterArrayList.get(2));
             increasedCostCard3.setText("COST +1 = TRUE");
    }




}
