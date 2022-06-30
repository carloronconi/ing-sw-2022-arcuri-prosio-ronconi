package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientNameIdConverter;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.viewevents.SetCharacterSettings;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.charactercards.*;
import it.polimi.ingsw.server.model.charactercards.effectarguments.EffectWithColor;
import it.polimi.ingsw.server.model.charactercards.effectarguments.EffectWithIsland;
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

    private PawnColor color = null;
    private UUID island = null;
    private UUID player = null;
    private ArrayList<ColorSwap> colorSwaps = null;

    private GameState gameState;
    private ClientNameIdConverter converter;
    private AvailableCharacter currentCharacter;
    private ArrayList<PawnColor> updatedEntrance;
    private ArrayList<PawnColor> updatedDining;
    private ArrayList<PawnColor> updatedCard;

    private Class<?> characterClass;


    /**
     * Initializes the CharacterSettings scene according to the Character Card played.
     * If the Card requires a Swap between Pawns from the Entrance or Dining of the player, those pawns are displayed
     * as well.
     * @param gameState to get the Entrance and Dining Pawns of a player
     * @param availableCharacter to get the Character Card chosen by the player
     */

    public void initializeSettings(GameState gameState, AvailableCharacter availableCharacter){
        this.gameState = gameState;
        this.currentCharacter = availableCharacter;
        converter = new ClientNameIdConverter(gameState);
        String nickname = getClientGui().getFinalNickname();
        UUID playerId = converter.nameToId(nickname, ClientNameIdConverter.ConverterSetting.PLAYER);



        HashMap<AvailableCharacter, ArrayList<PawnColor>> characterPawnsMap = gameState.getCharacterCardsStudents();

        String chosenCharacter = availableCharacter.name().toLowerCase(Locale.ROOT);
        System.out.println(chosenCharacter);

        String path = "/"+chosenCharacter+".jpg";
        card.setImage(new Image(String.valueOf(getClass().getResource(path))));
        cardName.setText(chosenCharacter);

        for (int i = 0; i < characterPawnsMap.size(); i++) {
            AvailableCharacter av = (AvailableCharacter) characterPawnsMap.keySet().toArray()[i];
            if(av.equals(availableCharacter)){
                updatedCard = new ArrayList<>(characterPawnsMap.get(av));
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
            updatedEntrance = new ArrayList<>(entranceMap);
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
            updatedDining = new ArrayList<>(diningMap);
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

        characterClass = availableCharacter.getCharacterClass();

        pawnColor.setMouseTransparent(true);
        pawnColor.setFocusTraversable(false);
        whereTo.setMouseTransparent(true);
        whereTo.setFocusTraversable(false);
        swapGive.setMouseTransparent(true);
        swapGive.setFocusTraversable(false);
        swapTake.setMouseTransparent(true);
        swapTake.setFocusTraversable(false);

        if (EffectWithColor.class.isAssignableFrom(characterClass)){
            pawnColor.setMouseTransparent(false);
            pawnColor.setFocusTraversable(true);
        }
        if (EffectWithIsland.class.isAssignableFrom(characterClass)){
            whereTo.setMouseTransparent(false);
            whereTo.setFocusTraversable(true);
        }
        if (SwapperCharacter.class.isAssignableFrom(characterClass)){
            swapGive.setMouseTransparent(false);
            swapGive.setFocusTraversable(true);
            swapTake.setMouseTransparent(false);
            swapTake.setFocusTraversable(true);
        }

    }


    /**
     * Converts the string input by the player as a PawnColor
     */
    public void settingPawnColor(){
        if (!EffectWithColor.class.isAssignableFrom(characterClass)) return;

        String string = pawnColor.getText().toUpperCase(Locale.ROOT);
        try{
            PawnColor tempColor = PawnColor.valueOf(string);
            if (Monk.class.isAssignableFrom(characterClass) && !gameState.getCharacterCardsStudents().get(AvailableCharacter.MONK).contains(tempColor)) return;
            if (Princess.class.isAssignableFrom(characterClass) && !gameState.getCharacterCardsStudents().get(AvailableCharacter.PRINCESS).contains(tempColor)) return;
            color = tempColor;
            pawnColor.clear();
        } catch(IllegalArgumentException | NullPointerException e){
            System.out.println("no color with that name");
        }

        System.out.println(color + " accepted");

    }



    /**
     * Converts the string input by the player as an Island UUID
     */
    public void settingIslandChosen(){
        if (!EffectWithIsland.class.isAssignableFrom(characterClass)) return;

        String islandName = whereTo.getText();
        island = converter.nameToId(islandName, ClientNameIdConverter.ConverterSetting.ISLAND); //returns null if it doesn't find island
        if (Witch.class.isAssignableFrom(characterClass) && island!=null && gameState.getBanOnIslands().get(island)) island = null;
        System.out.println(island);
        if (island!=null) whereTo.clear();
        else System.out.println("no island with that name");
    }


    /**
     * Clears the TextFields to allow the player to enter a new couple of swapping pawns and stores the input one
     * in an Array
     * @param ev clicked button by the player
     */
    public void swapClick(ActionEvent ev){
        //creates the swap Array
        if (currentCharacter != AvailableCharacter.JUGGLER && currentCharacter!= AvailableCharacter.MUSICIAN) return;
        if (colorSwaps!=null && colorSwaps.size()==currentCharacter.getMaxColorSwaps()) return;

        try{
            PawnColor give = PawnColor.valueOf(swapGive.getText().toUpperCase());
            PawnColor take = PawnColor.valueOf(swapTake.getText().toUpperCase());

            if (!updatedEntrance.contains(give)) return;
            if (currentCharacter == AvailableCharacter.JUGGLER && !updatedCard.contains(take)) return;
            if (currentCharacter == AvailableCharacter.MUSICIAN && !updatedDining.contains(take)) return;

            ColorSwap colorSwap = new ColorSwap(give, take);
            if (colorSwaps == null) colorSwaps = new ArrayList<>();
            colorSwaps.add(colorSwap);

            updatedEntrance.remove(give);
            if (currentCharacter == AvailableCharacter.JUGGLER) updatedCard.remove(take);
            else updatedDining.remove(take);
            //TODO: (nice to have) also remove children of give and take colors from pane

            swapGive.clear();
            swapTake.clear();
        } catch(IllegalArgumentException | NullPointerException e){
            System.out.println("no color with that name");
            //e.printStackTrace();
        }

    }

    /**
     * Sends a message to the server containing all the character settings and changes scene
     * @param e clicked button by the player
     */
    public void clickedButton(ActionEvent e) {

        if ((currentCharacter == AvailableCharacter.MUSICIAN || currentCharacter == AvailableCharacter.JUGGLER) && colorSwaps == null) colorSwaps = new ArrayList<>();
        getClientGui().getGuiView().notifyEventManager(new SetCharacterSettings(color, player, island, colorSwaps ));
        System.out.println(color);
        System.out.println(player);
        System.out.println(island);
        System.out.println(colorSwaps);

        new ChangeScene(getClientGui()).run();

    }




}
