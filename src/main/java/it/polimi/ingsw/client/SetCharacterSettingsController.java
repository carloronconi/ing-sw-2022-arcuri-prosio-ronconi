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


    public void initializeSettings(GameState gameState, AvailableCharacter availableCharacter){
        this.gameState = gameState;
        CliViewIdConverter converter = new CliViewIdConverter(gameState);
        String nickname = getClientGui().getFinalNickname();
        UUID playerId = converter.nameToId(nickname, CliViewIdConverter.converterSetting.PLAYER);



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
                if(gameState.getEntrances().containsKey(playerId)) {
                    for (PawnColor color : gameState.getEntrances().get(playerId).keySet()) {
                        int number = gameState.getEntrances().get(playerId).get(color);
                        for (int k = 0; k < number; k++) {
                            Circle circle = new Circle();
                            circle.setCenterX(50.0);
                            circle.setCenterY(50.0);
                            circle.setLayoutX(805.0);
                            circle.setLayoutY(520.0 + k*20.0);
                            circle.setRadius(16.0);
                            circle.setStroke(Color.BLACK);
                            circle.setStrokeType(StrokeType.INSIDE);
                            circle.setFill(Color.valueOf(color.toString()));

                            pane.getChildren().add(circle);
                        }

                    }
                }
            }

        if(chosenCharacter.equals("musician")){
            if(gameState.getDiningRooms().containsKey(playerId)){
                for (PawnColor color : gameState.getDiningRooms().get(playerId).keySet()) {
                    int number = gameState.getDiningRooms().get(playerId).get(color);
                    for (int k = 0; k < number; k++) {
                        Circle circle = new Circle();
                        circle.setCenterX(50.0);
                        circle.setCenterY(50.0);
                        circle.setLayoutX(805.0);
                        circle.setLayoutY(520.0 + k*20.0);
                        circle.setRadius(16.0);
                        circle.setStroke(Color.BLACK);
                        circle.setStrokeType(StrokeType.INSIDE);
                        circle.setFill(Color.valueOf(color.toString()));

                        pane.getChildren().add(circle);
                    }

                }
            }


        }


    }

    public String pawnColorChosen(){ return pawnColor.getText(); }
    public String whereToChosen(){ return  whereTo.getText(); }

    public void clickedButton(ActionEvent e) throws IOException {
        CliViewIdConverter converter = new CliViewIdConverter(gameState);

        //pawn
        String string = pawnColorChosen().toUpperCase(Locale.ROOT);
        color = PawnColor.valueOf(string);

        //player
        String nickname = getClientGui().getFinalNickname();
        player = converter.nameToId(nickname, CliViewIdConverter.converterSetting.PLAYER);

        //island UUID
        String islandName = whereToChosen();
        island = converter.nameToId(islandName, CliViewIdConverter.converterSetting.ISLAND);

        getClientGui().getGuiView().notifyEventManager(new SetCharacterSettings(color, player, island, colorSwaps ));

        new ChangeScene(getClientGui()).run();

    }

    public void swapClick(ActionEvent ev){
        //creates the swap Array
        colorSwaps.add(new ColorSwap(PawnColor.valueOf(whereToSwapGive().toUpperCase(Locale.ROOT)), PawnColor.valueOf(whereToSwapTake().toUpperCase(Locale.ROOT))));
        swapGive.clear();
        swapTake.clear();

    }

    public String whereToSwapGive(){
        return swapGive.getText();
    }

    public String whereToSwapTake(){
        return swapTake.getText();
    }
}
