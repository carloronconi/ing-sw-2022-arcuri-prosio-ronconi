package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.charactercards.*;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithColor;
import it.polimi.ingsw.model.charactercards.effectarguments.EffectWithIsland;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.viewevents.ChosenCharacter;
import it.polimi.ingsw.networkmessages.viewevents.SetCharacterSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;


public class SetCharacterSettingsController extends SceneController{

    @FXML Label cardName;
    @FXML ImageView card;
    @FXML AnchorPane pane;


    @FXML TextField pawnColor;
    @FXML TextField whereTo;

    @FXML TextField swapGive;
    @FXML TextField swapTake;
    @FXML Button swapBtn;
    @FXML Button btn;

    PawnColor color = null;
    UUID island = null;
    UUID player = null;
    ArrayList<ColorSwap> colorSwaps = null;

    private GameState gameState;
    CliViewIdConverter converter;


    /**
     * Initializes the CharacterSettings scene according to the Character Card played.
     *If the Card requires a Swap between Pawns from the Entrance or Dining of the player, those pawns are displayed
     * as well.
     * @param gameState to get the Entrance and Dining Pawns of a player
     * @param availableCharacter to get the Character Card chosen by the player
     */

    public void initializeSettings(GameState gameState, AvailableCharacter availableCharacter){
        this.gameState = gameState;
        converter = new CliViewIdConverter(gameState);
        String nickname = getClientGui().getFinalNickname();
        UUID playerId = converter.nameToId(nickname, CliViewIdConverter.ConverterSetting.PLAYER);



        HashMap<AvailableCharacter, ArrayList<PawnColor>> characterPawnsMap = gameState.getCharacterCardsStudents();

        String chosenCharacter = availableCharacter.name().toLowerCase(Locale.ROOT);
        System.out.println(chosenCharacter);

        String path = "/"+chosenCharacter+".jpg";
        card.setImage(new Image(String.valueOf(getClass().getResource(path))));
        cardName.setText(chosenCharacter);

        for (int i = 0; i < characterPawnsMap.size(); i++) {
            AvailableCharacter av = (AvailableCharacter) characterPawnsMap.keySet().toArray()[i];
            if(av.equals(availableCharacter)){
                for (int j = 0; j < characterPawnsMap.get(av).size(); j++) {
                    Circle circle = new Circle();
                    circle.setCenterX(50.0);
                    circle.setCenterY(50.0);
                    circle.setLayoutX(1204.0 + 20.0*j);
                    circle.setLayoutY(310.0);
                    circle.setRadius(16.0);
                    circle.setStroke(Color.BLACK);
                    circle.setStrokeType(StrokeType.INSIDE);
                    circle.setFill(Color.valueOf(characterPawnsMap.get(av).get(j).toString()));

                    pane.getChildren().add(circle);

                }
            }
        }

        //if juggler or musician
        if(chosenCharacter.equals("juggler")|| chosenCharacter.equals("musician")) {

            //list of colors
            ArrayList<PawnColor> entranceMap = new ArrayList<>();
            if(gameState.getEntrances().containsKey(playerId)){
              for(PawnColor color : gameState.getEntrances().get(playerId).keySet()){
                  int number = gameState.getEntrances().get(playerId).get(color);
                  for (int i = 0; i < number; i++) {
                      entranceMap.add(color);
                  }
              }
            }
            System.out.println(entranceMap);
            for (int i = 0; i < entranceMap.size(); i++) {
                Circle circle = new Circle();
                circle.setCenterX(50.0);
                circle.setCenterY(50.0);
                circle.setLayoutX(805.0);
                circle.setLayoutY(520.0 + i*30.0);
                circle.setRadius(16.0);
                circle.setStroke(Color.BLACK);
                circle.setStrokeType(StrokeType.INSIDE);
                circle.setFill(Color.valueOf(entranceMap.get(i).toString()));

                pane.getChildren().add(circle);
            }

            }

        if(chosenCharacter.equals("musician")){
            ArrayList<PawnColor> diningMap = new ArrayList<>();
            if(gameState.getDiningRooms().containsKey(playerId)){
                for(PawnColor color : gameState.getDiningRooms().get(playerId).keySet()){
                    int number = gameState.getDiningRooms().get(playerId).get(color);
                    for (int i = 0; i < number; i++) {
                        diningMap.add(color);
                    }
                }
            }
            System.out.println(diningMap);
            for (int i = 0; i < diningMap.size(); i++) {
                Circle circle = new Circle();
                circle.setCenterX(50.0);
                circle.setCenterY(50.0);
                circle.setLayoutX(1100.0);
                circle.setLayoutY(520.0 + i*30.0);
                circle.setRadius(16.0);
                circle.setStroke(Color.BLACK);
                circle.setStrokeType(StrokeType.INSIDE);
                circle.setFill(Color.valueOf(diningMap.get(i).toString()));

                pane.getChildren().add(circle);
            }

        }


    }

    /**
     *
     * @return string input by the player as the color of the pawn  on which apply the Character Card Effect
     */
    public String pawnColorChosen(){return pawnColor.getText(); }

    /**
     * Converts the string input by the player as a PawnColor
     */
    public void settingPawnColor(){
        System.out.println(pawnColor.getText().toUpperCase(Locale.ROOT));
        String string = pawnColorChosen().toUpperCase(Locale.ROOT);
        color = PawnColor.valueOf(string);
        System.out.println(color);

    }

    /**
     *
     * @return string input by the player as the island on which apply the Character Card Effect
     */
    public String whereToChosen(){ return  whereTo.getText(); }

    /**
     * Converts the string input by the player as an Island UUID
     */
    public void settingIslandChosen(){
        System.out.println(whereTo.getText());
        String islandName = whereToChosen();
        island = converter.nameToId(islandName, CliViewIdConverter.ConverterSetting.ISLAND);
        System.out.println(island);

    }

    /**
     *
     * @return PawnColor that will be swapped from the player's Entrance according to the Character Card Effect
     */
    public PawnColor pawnToGive(){
        String pawnGive = swapGive.getText().toUpperCase(Locale.ROOT);
        PawnColor pawn = PawnColor.valueOf(pawnGive);

        return pawn;
    }

    /**
     *
     * @return PawnColor that will be swapped from the player's Dining or from the set of Pawns on the Juggler Card
     * according to the Character Card Effect
     */
    public PawnColor pawnToTake(){
        String pawnTake = swapTake.getText().toUpperCase(Locale.ROOT);
        PawnColor pawn1 = PawnColor.valueOf(pawnTake);

        return pawn1;
    }

    /**
     * Clears the TextFields to allow the player to enter a new couple of swapping pawns and stores the input one
     * in an Array
     * @param ev clicked button by the player
     */
    public void swapClick(ActionEvent ev){
        //creates the swap Array
        ColorSwap colorSwap = new ColorSwap(pawnToGive(), pawnToTake());
        colorSwaps = new ArrayList<>();
        colorSwaps.add(colorSwap);
        System.out.println(colorSwap);
        swapGive.clear();
        swapTake.clear();

    }

    /**
     * Sends a message to the server containing all the character settings and changes scene
     * @param e clicked button by the player
     */
    public void clickedButton(ActionEvent e) {

        getClientGui().getGuiView().notifyEventManager(new SetCharacterSettings(color, player, island, colorSwaps ));
        System.out.println(color);
        System.out.println(player);
        System.out.println(island);
        System.out.println(colorSwaps);

        new ChangeScene(getClientGui()).run();

    }




}