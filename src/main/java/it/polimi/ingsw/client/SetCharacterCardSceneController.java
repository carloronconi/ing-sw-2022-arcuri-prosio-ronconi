package it.polimi.ingsw.client;

import it.polimi.ingsw.model.charactercards.AvailableCharacter;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class SetCharacterCardSceneController extends SceneController {
    @FXML ImageView card1;
    @FXML ImageView card2;
    @FXML ImageView card3;

    @FXML
    TextArea explainCard1;

    private ArrayList<String> characterArrayList = new ArrayList<>();
    private ArrayList<ImageView> cardViews = new ArrayList<>();

    public ImageView getCard1() {
        return card1;
    }

    public ImageView getCard2() {
        return card2;
    }

    public ImageView getCard3() {
        return card3;
    }

    public void initializeCards(GameState gameState){
        cardViews.add(card1);
        cardViews.add(card2);
        cardViews.add(card3);


        HashMap<AvailableCharacter, Boolean> availableCharacterMap = gameState.getCharacterCards();
        for (int i = 0; i < availableCharacterMap.size(); i++) {
            AvailableCharacter av = (AvailableCharacter) availableCharacterMap.keySet().toArray()[i];
            if(availableCharacterMap.get(av)){
                String characterName = av.name().toLowerCase(Locale.ROOT);
                characterArrayList.add(characterName);
            }
            if(characterArrayList.size()==3) break;
        }

        for (int i = 0; i < characterArrayList.size(); i++) {
            for(ImageView imageView : cardViews){
                String str = imageView.getId();
                int numberOnly = Integer.parseInt(str.replaceAll("[^0-9]", ""));
                if(numberOnly == (i+1)){
                            String characterI = characterArrayList.get(i);
                            String imageNameI = "/"+characterI+".jpg";
                            imageView.setImage(new Image(String.valueOf(getClass().getResource(imageNameI))));

                            explainCard1.append(characterI);

                }
            }

        }




    }
}
