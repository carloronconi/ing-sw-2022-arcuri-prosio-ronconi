package it.polimi.ingsw.client;

import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.viewevents.ChosenCharacter;
import it.polimi.ingsw.networkmessages.viewevents.SetAssistantCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private String chosenCharacter;



    public void initialize(){
        cardLab.add(card1);
        cardLab.add(card2);
        cardLab.add(card3);

        cardImage.add(card1m);
        cardImage.add(card2m);
        cardImage.add(card3m);


    }

    private HashMap<AvailableCharacter, Boolean> availableCharacterMap ;

    /**
     * Displays three Character Cards randomly chosen by the server to the player.
     * @param gameState to get the current amount of money owned by each player and the three Character Cards
     */
    public void initializeCards(GameState gameState)  {

        //cards

        System.out.println("tutto ok!!!");
        availableCharacterMap = gameState.getCharacterCards();
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
            chosenCharacter = characterArrayList.get(0).toUpperCase(Locale.ROOT);
            increasedCostCard1.setText("COST +1 = TRUE");

                }
        public void clickedCard2(){
            card2m.setOpacity(0.4d);
            chosenCharacter= characterArrayList.get(1).toUpperCase(Locale.ROOT);
            increasedCostCard2.setText("COST +1 = TRUE");
        }

         public void clickedCard3(){
             card3m.setOpacity(0.4d);
             chosenCharacter = characterArrayList.get(2).toUpperCase(Locale.ROOT);
             increasedCostCard3.setText("COST +1 = TRUE");
    }

    private AvailableCharacter findCorrespondentCharacter(String string){
        for(int i =0; i< availableCharacterMap.size(); i++){
            if (availableCharacterMap.keySet().toArray()[i].toString().equals(string)){

                return (AvailableCharacter) availableCharacterMap.keySet().toArray()[i];
            }
        }
        return null;
    }


    /**
     * If the player decides not to use a Character Cards, by clicking the button without having selected
     * any card he will be redirected to the gameBoard.
     * If the player has selected a Character Card, he is redirected to the CharacterSettings scene
     * @param e clicked button by the player
     * @throws IOException
     */
    public void clickedButton(ActionEvent e) throws IOException {
        AvailableCharacter av = findCorrespondentCharacter(chosenCharacter);

        getClientGui().getGuiView().notifyEventManager(new ChosenCharacter(av));

        new ChangeScene(getClientGui()).run();

    }




}
