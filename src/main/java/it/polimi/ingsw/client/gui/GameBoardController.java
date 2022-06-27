package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientNameIdConverter;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.TowerColor;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import it.polimi.ingsw.networkmessages.viewevents.ChosenCloud;
import it.polimi.ingsw.networkmessages.viewevents.MovedMotherNature;
import it.polimi.ingsw.networkmessages.viewevents.MovedStudent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GameBoardController extends SceneController{
    public ImageView cloud2;
    public ImageView cloud1;
    public ImageView card1;
    public ImageView card2;
    public Label player1;
    public Label player2;
    public ImageView card3;
    public Label player3;
    public Label player3board;
    public Pane entrance3;
    public Pane towers3;
    public Pane dinings3;
    public Pane professors3;
    public ImageView cloud3;
    @FXML Label player1board;
    @FXML Label player2board;
    @FXML Pane boardPane;
    @FXML VBox vbox;
    @FXML Pane islandRow0;
    @FXML Pane islandRow1;
    @FXML Pane islandRow2;
    @FXML Pane entrance1;
    @FXML Pane dinings1;
    @FXML Pane professors1;
    @FXML Pane towers1;
    //@FXML Circle MN;
    //@FXML ImageView cloud1;
    //@FXML ImageView cloud2;
    @FXML ImageView image1;
    @FXML ImageView image2;
    @FXML ImageView image3;
    @FXML ImageView image4;
    @FXML ImageView image5;
    @FXML ImageView image6;
    @FXML ImageView image7;
    @FXML ImageView image8;
    @FXML ImageView image9;
    @FXML ImageView image10;
    @FXML ImageView image11;
    @FXML ImageView image12;

    @FXML Pane entrance2;
    @FXML Pane dinings2;
    @FXML Pane professors2;
    @FXML Pane towers2;
    @FXML Rectangle professorsRectangle;

    private final List<Pane> islands = new ArrayList<>();
    private List<ImageView> islandImage = new ArrayList<>();
    private final List<Pane> board = new ArrayList<>();
    private final int MAX_SIZE = 130;
    private final ArrayList<Circle> bag = new ArrayList<>(MAX_SIZE);
   // private final List<Pane> entr1 = new ArrayList<>();
   // private final List<Pane> entr2 = new ArrayList<>();
    private final Set<UUID> players = new HashSet<>();
    private final ArrayList<Pane> towersPane = new ArrayList<>();
    private final List<Pane> prof = new ArrayList<>();
    private final List<Pane> entrances = new ArrayList<>();


    //private ArrayList<Integer> numOfPawnsInDining;
    private HashMap<String, Integer> pawnsInDining;

    private static ClientNameIdConverter initialIslandConverter;
    private static boolean firstTime = true;

    String red;
    String yellow;
    String blue;
    String green;
    String purple;

    private List<Pane> panes = new ArrayList<>();

    private static GameBoardState gameBoardState;



    @FXML
    public void initialize() {
        System.out.println(boardPane.getChildren());
        System.out.println(islandRow0.getChildren());
        pawnsInDining = new HashMap<>();
        //numOfPawnsInDining = new ArrayList<>();
        pawnsInDining.put(red, 0);
        pawnsInDining.put(yellow,0);
        pawnsInDining.put(green,0);
        pawnsInDining.put(blue,0);
        pawnsInDining.put(purple, 0);

        towersPane.add(towers1);
        towersPane.add(towers2);
        if (towers3!=null) towersPane.add(towers3);

        prof.add(professors1);
        prof.add(professors2);
        if (professors3!=null) prof.add(professors3);

        entrances.add(entrance1);
        entrances.add(entrance2);
        if (entrance3!=null) entrances.add(entrance3);

        //entr1.add(entrance1);
        //entr2.add(entrance2);
        islands.add(islandRow0);
        islands.add(islandRow1);
        islands.add(islandRow2);

        board.add(dinings1);
        board.add(dinings2);
        if (dinings3!=null) board.add(dinings3);

        panes.add(islandRow0);
        panes.add(islandRow1);
        panes.add(islandRow2);
        panes.add(entrance1);
        panes.add(dinings1);

        vbox.setMaxWidth(1500.0d);

        diningTables.add(green1);
        diningTables.add(red1);
        diningTables.add(yellow1);
        diningTables.add(purple1);
        diningTables.add(blue1);

        islandImage.add(image1);
        islandImage.add(image2);
        islandImage.add(image3);
        islandImage.add(image4);
        islandImage.add(image5);
        islandImage.add(image6);
        islandImage.add(image7);
        islandImage.add(image8);
        islandImage.add(image9);
        islandImage.add(image10);
        islandImage.add(image11);
        islandImage.add(image12);



        //pawns.add(circle);
        //pawns.add(MN);
        for (int i = 0; i < 26; i++) {
            Circle c = new Circle();
            c.setFill(Color.RED);
            bag.add(c);
            Circle c1 = new Circle();
            c1.setFill(Color.BLUE);
            bag.add(c1);
            Circle c2 = new Circle();
            c2.setFill(Color.GREEN);
            bag.add(c2);
            Circle c3 = new Circle();
            c3.setFill(Color.YELLOW);
            bag.add(c3);
            Circle c4 = new Circle();
            c4.setFill(Color.PURPLE);
            bag.add(c4);

        }

    }

    private HashMap<UUID, String> playersNick;



    public void updateBoard(GameState gameState) { //call it from lambda expression in previous controller nextScene

        int bag = gameState.getBag();
        ClientNameIdConverter converter = new ClientNameIdConverter(gameState);

        if (gameBoardState == null){
            gameBoardState = new GameBoardState(gameState.getNicknames().size());
        } else if (getClientGui().getGuiView().isInvalidBoardMove()) gameBoardState.previousState();
        System.out.println(gameBoardState.getBoardState());

        //ISLANDS
        /*
        if (initialIslandConverter == null) {
            initialIslandConverter = new CliViewIdConverter(gameState);
            System.out.println("initial island ids: " + gameState.getIslands().keySet());
        }*/

        boolean placedMN = false;

        for (Pane p : islands) {
            for (Node r : p.getChildrenUnmodifiable()) {
                for (int i = 0; i < getClientGui().getGuiView().getInitialStateConverter().getSize(ClientNameIdConverter.ConverterSetting.ISLAND); i++) {
                    int islandNumber = i + 1;
                    String islandName = "island" + islandNumber;
                    if (r.getId() != null && r.getId().contains(islandName)) {
                        LinkedHashMap<UUID, ArrayList<PawnColor>> islandIGameModel = gameState.getIslands();

                        UUID islandId = getClientGui().getGuiView().getInitialStateConverter().nameToId(islandName, ClientNameIdConverter.ConverterSetting.ISLAND);
                        //UUID islandId = converter.nameToId(islandName, CliViewIdConverter.converterSetting.ISLAND);
                        //System.out.println("current island ids: " + gameState.getIslands().keySet());

                        if (!islandIGameModel.containsKey(islandId)){ //island has been eliminated
                            System.out.println("deleted island detected: " + islandName);
                            int numberOnly = Integer.parseInt(islandName.replaceAll("[^0-9]", ""));
                            System.out.println(numberOnly);

                            if(numberOnly==1) islandRow0.getChildren().remove(image1);
                            else if(numberOnly==2) islandRow0.getChildren().remove(image2);
                            else if(numberOnly==3) islandRow0.getChildren().remove(image3);
                            else if(numberOnly==4) islandRow0.getChildren().remove(image4);
                            else if(numberOnly==5) islandRow0.getChildren().remove(image5);
                            else if(numberOnly==6) islandRow1.getChildren().remove(image6);
                            else if(numberOnly==7) islandRow2.getChildren().remove(image7);
                            else if(numberOnly==8) islandRow2.getChildren().remove(image8);
                            else if(numberOnly==9) islandRow2.getChildren().remove(image9);
                            else if(numberOnly==10) islandRow2.getChildren().remove(image10);
                            else if(numberOnly==11) islandRow2.getChildren().remove(image11);
                            else if(numberOnly==12) islandRow1.getChildren().remove(image12);
                            else System.out.println("no image to delete");

                           // Rectangle rectangle = new Rectangle(190.0,190.0, Color.valueOf("#6abade"));
                            //rectangle.setLayoutX(r.getLayoutX()-30.0);
                           // rectangle.setLayoutY(r.getParent().getLayoutY());

                           // boardPane.getChildren().add(rectangle);
                            //boardPane.getChildren().remove(r);
                        } else{
                            //loop on all colors in island
                            for (int j = 0; j < islandIGameModel.get(islandId).size(); j++) {
                                Circle c = new Circle();
                                c.setCenterX(50.0);
                                c.setCenterY(50.0);
                                c.setLayoutX(r.getLayoutX() + (j * 15.0));
                                c.setLayoutY(r.getParent().getLayoutY() /*+ (j * 35.0)*/);
                                c.setRadius(16.0);
                                c.setStroke(Color.BLACK);
                                c.setStrokeType(StrokeType.INSIDE);
                                c.setFill(Color.valueOf(islandIGameModel.get(islandId).get(j).toString()));

                                boardPane.getChildren().add(c);
                            }

                            //add mother nature if present
                            String MNIslandName = getClientGui().getGuiView().getInitialStateConverter().idToName(gameState.getMotherNaturePosition(), ClientNameIdConverter.ConverterSetting.ISLAND);
                            if(islandName.equals(MNIslandName) && !placedMN){
                                placedMN = true;
                                System.out.println("MN position: " + gameState.getMotherNaturePosition());
                                System.out.println("current island: " + islandId);
                                System.out.println("coordinates: x = " + r.getLayoutX() + " y = " + r.getParent().getLayoutY());

                                Circle motherNature = new Circle();
                                motherNature.setCenterX(50.0);
                                motherNature.setCenterY(50.0);
                                motherNature.setLayoutX(r.getParent().getLayoutX() + r.getLayoutX() + 10);
                                motherNature.setLayoutY(r.getParent().getLayoutY() + r.getLayoutY() + 30);
                                motherNature.setRadius(21);
                                motherNature.setStroke(Color.GREY);
                                motherNature.setStrokeType(StrokeType.INSIDE);
                                motherNature.setStrokeWidth(4);
                                motherNature.setFill(Color.ORANGE);
                                motherNature.setOnMouseDragged(this::movePiece);
                                motherNature.setOnMousePressed(this::startMovingPiece);
                                motherNature.setOnMouseReleased(this::finishMovingPiece);

                                pawns.add(motherNature);
                                boardPane.getChildren().add(motherNature);
                            }

                            //add towers if present
                            if (gameState.getIslandOwners().get(islandId)!= null){
                                for (int itr = 0; itr< gameState.getIslandSizes().get(islandId); itr++){
                                    Circle c = new Circle();
                                    c.setLayoutX(r.getLayoutX() + (itr+1) * 30.0);
                                    c.setLayoutY(r.getParent().getLayoutY() + /*(itr+1.0) * */ 60);
                                    c.setRadius(16.0);
                                    c.setStroke(Color.BLACK);
                                    c.setStrokeType(StrokeType.INSIDE);
                                    c.setFill(Color.valueOf(gameState.getColorPlayersTowers().get(gameState.getIslandOwners().get(islandId)).toString()));
                                    c.setCenterX(50.0);
                                    c.setCenterY(50.0);

                                    boardPane.getChildren().add(c);
                                }
                            }
                        }
                    }
                }
            }
        }

        //CLOUDS

        for (Pane p : islands) {
            for (Node r : p.getChildrenUnmodifiable()) {
                for (int i = 0; i < gameState.getClouds().size(); i++) {
                    int cloudNumber = i + 1;
                    String cloudName = "cloud" + cloudNumber;
                    if (r.getId() != null && r.getId().contains(cloudName)) {
                        HashMap<UUID, ArrayList<PawnColor>> cloudIGameModel = gameState.getClouds();
                        UUID cloudID = converter.nameToId(cloudName, ClientNameIdConverter.ConverterSetting.CLOUD);
                        for (int j = 0; j < cloudIGameModel.get(cloudID).toArray().length; j++) {
                            Circle c = new Circle();
                            c.setCenterX(50.0);
                            c.setCenterY(50.0);
                            c.setLayoutX(r.getLayoutX() + (j * 30.0));
                            c.setLayoutY(r.getParent().getLayoutY() + (j * 35.0));
                            c.setRadius(16.0);
                            c.setStroke(Color.BLACK);
                            c.setStrokeType(StrokeType.INSIDE);
                            c.setFill(Color.valueOf(cloudIGameModel.get(cloudID).get(j).toString()));

                            //c.setOnMouseDragged(this::movePiece);
                            //c.setOnMousePressed(this::startMovingPiece);
                            //c.setOnMouseReleased(this::finishMovingPiece);

                            boardPane.getChildren().add(c);
                            pawns.add(c);


                        }
                        if (cloudIGameModel.get(cloudID).isEmpty()) r.setMouseTransparent(true);

                    }
                }
            }
        }

        //PLAYERS
        for (int i = 0; i < gameState.getNicknames().size(); i++) {
            playersNick = gameState.getNicknames();
            System.out.println("PLAYERS SIZE: "+playersNick.size());

        }

        //ENTRANCES
        initializeEntrance(gameState);

        //PROFESSORS

        EnumMap<PawnColor, UUID> professorOwnersBoard  = gameState.getProfessorOwners();
        int index = 0;
        for(PawnColor color : professorOwnersBoard.keySet()){
            if(professorOwnersBoard.get(color) == null){
                Circle c = new Circle();
                c.setCenterX(50.0);
                c.setCenterY(50.0);
                c.setRadius(16.0);
                c.setStroke(Color.GREY);
                c.setStrokeType(StrokeType.INSIDE);
                c.setStrokeWidth(5.0);
                c.setFill(Color.valueOf(color.name()));
                Random random = new Random();
                //double addX = random.nextDouble(0.0, 75.0);
                //double addY = random.nextDouble(0.0, 110.0);
                c.setLayoutX(professorsRectangle.getLayoutX()+ index*15.0);
                c.setLayoutY(professorsRectangle.getParent().getLayoutY() + index*40.0 - ((index+1)*10.0));

                boardPane.getChildren().add(c);
                pawns.add(c);
                index++;
            } else{   //add professor to corresponding player's school
                if(professorOwnersBoard.get(color) != null){
                    for (int i = 0; i < playersNick.size(); i++) {

                        Object object = playersNick.keySet().toArray()[i];
                        UUID playerNickID = (UUID) object;

                        if (playerNickID.equals(professorOwnersBoard.get(color))){
                            System.out.println("FIN QUI TUTTO OK");
                            System.out.println(color.name());
                            int playerNumber = i+1;
                            for(Pane p : prof){
                                String str = p.getId();
                                int numberOnly = Integer.parseInt(str.replaceAll("[^0-9]", ""));
                                System.out.println(numberOnly);
                                System.out.println(playerNumber);
                                System.out.println("OK");
                                if(numberOnly==playerNumber){
                                    System.out.println("OK2");
                                    for(Node rect : p.getChildrenUnmodifiable()){
                                        System.out.println(rect.getId());
                                        if(rect.getId().equals(color.name())){
                                            Circle circle = new Circle();
                                            circle.setCenterX(50.0);
                                            circle.setCenterY(50.0);
                                            circle.setRadius(16.0);
                                            circle.setStroke(Color.GREY);
                                            circle.setStrokeType(StrokeType.INSIDE);
                                            circle.setStrokeWidth(5.0);
                                            circle.setFill(Color.valueOf(color.name()));
                                            circle.setLayoutX(rect.getParent().getLayoutX() -20.0);
                                            circle.setLayoutY(rect.getParent().getLayoutY() + rect.getLayoutY() - 15.0);

                                            boardPane.getChildren().add(circle);
                                            pawns.add(circle);

                                        }
                                    }

                                }
                            }


                    }


                        }
                    }
                }

            }


        //DINING ROOMS
        HashMap<UUID, EnumMap<PawnColor, Integer>> gameStateDiningRooms = gameState.getDiningRooms();

        for(UUID id : gameStateDiningRooms.keySet()){
            for (int i = 0; i < playersNick.size(); i++) {

                Object object = playersNick.keySet().toArray()[i];
                UUID playerNickID = (UUID) object;
                if (playerNickID.equals(id)){
                    int playerNumber = i+1;
                    for(Pane p : board){
                        String str = p.getId();
                        int numberOnly = Integer.parseInt(str.replaceAll("[^0-9]", ""));
                        System.out.println(numberOnly);
                        System.out.println(playerNumber);
                        System.out.println("OK");
                        if(numberOnly==playerNumber){
                            System.out.println("OK2");
                            for(Node rect : p.getChildrenUnmodifiable()){
                                System.out.println(rect.getId());
                                for(PawnColor color : gameStateDiningRooms.get(id).keySet()){
                                    String string = color.toString().toLowerCase(Locale.ROOT);
                                    System.out.println(string);
                                    String name = string + playerNumber;
                                    if(rect.getId()!= null && rect.getId().equals(name)){ //PROBLEM HERE - I THINK CAPITAL LETTERS FOR PAWN NAME VS NOT FOR IDS
                                        for (int j = 0; j < gameStateDiningRooms.get(id).get(color); j++) {
                                            Circle circle = new Circle();
                                            circle.setCenterX(50.0);
                                            circle.setCenterY(50.0);
                                            circle.setRadius(16.0);
                                            circle.setStroke(Color.BLACK);
                                            circle.setStrokeType(StrokeType.INSIDE);
                                            circle.setFill(Color.valueOf(color.name()));
                                            circle.setLayoutX(rect.getParent().getLayoutX() + j*24.0 - 5.0);
                                            circle.setLayoutY(rect.getParent().getLayoutY() + rect.getLayoutY()- 20.0 );

                                            boardPane.getChildren().add(circle);
                                            pawns.add(circle);
                                        }
                                    }
                                }


                                }
                            }

                        }



                    }

                }

            }


        //TOWERS - add method to interleave towers with towers used

        LinkedHashMap<UUID, Integer> numOfTowersUsed = gameState.getNumOfTowersUsed();
        LinkedHashMap<UUID, TowerColor> ColorPlayersTower = gameState.getColorPlayersTowers();

        for(UUID id : ColorPlayersTower.keySet()) {
            for (int i = 0; i < playersNick.size(); i++) {

                Object object = playersNick.keySet().toArray()[i];
                UUID playerNickID = (UUID) object;
                if (playerNickID.equals(id)) {
                    int playerNumber = i+1;
                    for(Pane p : towersPane){
                        String str = p.getId();
                        int numberOnly = Integer.parseInt(str.replaceAll("[^0-9]", ""));
                        System.out.println(numberOnly);
                        System.out.println(playerNumber);
                        System.out.println("OK");
                        if(numberOnly==playerNumber) {
                            for(Node rect : p.getChildrenUnmodifiable()){
                                int totTowers = player3==null? 8:6;
                                for (int j = 0; j < (totTowers - numOfTowersUsed.get(id)); j++) {
                                    Circle c = new Circle();
                                    c.setLayoutX(rect.getParent().getLayoutX() );
                                    c.setLayoutY(rect.getParent().getLayoutY() + (j * 20) );
                                    c.setRadius(16.0);
                                    c.setStroke(Color.BLACK);
                                    c.setStrokeType(StrokeType.INSIDE);
                                    c.setFill(Color.valueOf(ColorPlayersTower.get(id).toString()));
                                    c.setCenterX(50.0);
                                    c.setCenterY(50.0);

                                    boardPane.getChildren().add(c);
                                    pawns.add(c);

                                }

                            }
                        }

                    }


                }

            }
        }

        //CARDS

        HashMap<UUID, Integer> playedAssistantCards = gameState.getPlayedAssistantCards();
        HashMap<UUID, String> playerNicknames = gameState.getNicknames();

        Iterator<UUID> iterator = playerNicknames.keySet().iterator();
        UUID id1 = iterator.next();
        UUID id2 = iterator.next();
        UUID id3 = null;
        if (player3!=null) id3 = iterator.next();

        player1.setText(playerNicknames.get(id1));
        player2.setText(playerNicknames.get(id2));
        if (player3!=null) player3.setText(playerNicknames.get(id3));

        player1board.setText(playerNicknames.get(id1));
        player2board.setText(playerNicknames.get(id2));
        if (player3!=null) player3board.setText(playerNicknames.get(id3));

        String cardName1 = "/Assistente(" + playedAssistantCards.get(id1) + ")-min.png";
        String cardName2 = "/Assistente(" + playedAssistantCards.get(id2) + ")-min.png";
        String cardName3 = null;
        if (player3!=null) cardName3 = "/Assistente(" + playedAssistantCards.get(id3) + ")-min.png";

        System.out.println(cardName1);
        System.out.println(cardName2);

        if (playedAssistantCards.get(id1)!=null) card1.setImage(new Image(String.valueOf(getClass().getResource(cardName1))));
        if (playedAssistantCards.get(id2)!=null) card2.setImage(new Image(String.valueOf(getClass().getResource(cardName2))));
        if (player3!=null && playedAssistantCards.get(id3)!=null) card3.setImage(new Image(String.valueOf(getClass().getResource(cardName3))));
    }


    private void initializeEntrance(GameState gameState){
        HashMap<UUID, ArrayList<PawnColor>> entranceHash = new HashMap<>();
        for (UUID id : gameState.getEntrances().keySet()){
            ArrayList<PawnColor> list = new ArrayList<>();
            for (PawnColor color : gameState.getEntrances().get(id).keySet()){
                //System.out.println(color);
                int number = gameState.getEntrances().get(id).get(color);
                //System.out.println(number);

                for (int k = 0; k<number; k++){
                    list.add(color);
                }

            }
            entranceHash.put(id, list);
            //System.out.println(entranceHash);

        }
        for(UUID id : entranceHash.keySet()) {
            for (int i = 0; i < playersNick.size(); i++) {
                Object object = playersNick.keySet().toArray()[i];
                UUID playerNickID = (UUID) object;
                if (playerNickID.equals(id)) {
                    int playerNumber = i + 1;
                    for (Pane p : entrances) {
                        String str = p.getId();
                        int numberOnly = Integer.parseInt(str.replaceAll("[^0-9]", ""));
                        //System.out.println(numberOnly);
                        //System.out.println(playerNumber);
                        //System.out.println("OK");
                        if (numberOnly == playerNumber) {
                            for (int j = 0; j < entranceHash.get(id).size(); j++) {
                                Circle c = new Circle();
                                c.setCenterX(50.0);
                                c.setCenterY(50.0);
                                c.setRadius(16.0);
                                c.setStroke(Color.BLACK);
                                c.setStrokeType(StrokeType.INSIDE);
                                c.setFill(Color.valueOf(entranceHash.get(id).get(j).toString()));

                                c.setLayoutY(p.getLayoutY() + j*20.0 );
                                c.setLayoutX(p.getLayoutX() + (j*15.0) - ((j+1)* 10.0));

                                c.setOnMouseDragged(this::movePiece);
                                c.setOnMousePressed(this::startMovingPiece);
                                c.setOnMouseReleased(this::finishMovingPiece);

                                boardPane.getChildren().add(c);
                                pawns.add(c);
                            }

                        }

                    }

                }
            }
        }

    }

    private int number(Rectangle r) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(r.getId().toString());
        int rectNumber;
        while (m.find()) {
            rectNumber = Integer.parseInt(m.group());
            System.out.println(rectNumber);

            return rectNumber;

        }
        return -1;
    }



    private Point2D offset = new Point2D(0.0d, 0.0d);
    private boolean movingPiece = false;


    public void leaveBoard(MouseEvent evt) {
        if(pawns.contains(evt.getSource())) {
            Circle circle = (Circle) evt.getSource();
            if (movingPiece) {
                final Timeline timeline = new Timeline();

                offset = new Point2D(0.0d, 0.0d);
                movingPiece = false;

                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(200),
                                new KeyValue(circle.layoutXProperty(), circle.getLayoutX()),
                                new KeyValue(circle.layoutYProperty(), circle.getLayoutY()),
                                new KeyValue(circle.opacityProperty(), 1.0d))


                );
                timeline.play();
            }
        }
    }

    private Rectangle currRect;
    private Circle c2;

    @FXML
    public void startMovingPiece(MouseEvent evt) {
        if(pawns.contains(evt.getSource())) {
            Circle circle = (Circle) evt.getSource();
            circle.setOpacity(0.4d);
            offset = new Point2D(evt.getX(), evt.getY());

            movingPiece = true;
        }
    }

    @FXML
    public void movePiece(MouseEvent evt) {
        if(pawns.contains(evt.getSource())) {
            Circle circle = (Circle) evt.getSource();
            Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
            Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());


            Point2D mousePoint_p = circle.localToParent(mousePoint);
            circle.relocate(mousePoint_p.getX() - offset.getX(), mousePoint_p.getY() - offset.getY());

        }
    }


    public Rectangle finishMovingPiece(MouseEvent evt) {

        if(pawns.contains(evt.getSource())) {

            Circle circle = (Circle) evt.getSource();
            offset = new Point2D(0.0d, 0.0d);

            Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
            Point2D mousePointScene = circle.localToScene(mousePoint);

            final Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100),
                            new KeyValue(circle.layoutXProperty(), circle.getLayoutX()),
                            new KeyValue(circle.layoutYProperty(), circle.getLayoutY()),
                            new KeyValue(circle.opacityProperty(), 1.0d))
            );


            for(Pane p : panes) {
                for(Node rect : p.getChildrenUnmodifiable()) {
                    if (rect != null) {

                        Point2D rectScene = rect.localToScene(rect.getLayoutX(), rect.getLayoutY());
                        Point2D parent = boardPane.sceneToLocal(rectScene.getX(), rectScene.getY());



                    } else {
                        timeline.getKeyFrames().add(
                                new KeyFrame(Duration.millis(100),
                                        new KeyValue(circle.opacityProperty(), 1.0d))
                        );
                    }
                    timeline.play();

                    movingPiece = false;
                }
            }


            try {
                Color c = (Color) circle.getFill();
                String hex = String.format( "#%02X%02X%02X",
                        (int)( c.getRed() * 255 ),
                        (int)( c.getGreen() * 255 ),
                        (int)( c.getBlue() * 255 ) );
                System.out.println(hex);
                PawnColor pawnColor = null;
                switch (hex) {
                    case "#FF0000":
                        pawnColor = PawnColor.RED;
                        break;
                    case "#008000":
                        pawnColor = PawnColor.GREEN;
                        break;
                    case "#0000FF":
                        pawnColor = PawnColor.BLUE;
                        break;
                    case "#FFFF00":
                        pawnColor = PawnColor.YELLOW;
                        break;
                    case "#800080":
                        pawnColor = PawnColor.PURPLE;
                        break;
                }

                printWhere(circle.getLayoutX(), circle.getLayoutY(), pawnColor);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


        return currRect;


    }


    private ArrayList<Circle> pawns = new ArrayList<>();


    //implementation swap pieces for update - diminish bag and update

    private ArrayList<Rectangle> diningTables = new ArrayList<>();
    @FXML Rectangle green1;
    @FXML Rectangle red1;
    @FXML Rectangle yellow1;
    @FXML Rectangle purple1;
    @FXML Rectangle blue1;

    public void printWhere(Double x, Double y, PawnColor color) throws IOException {
        for (Pane p : islands) {
            for (Node cell : p.getChildrenUnmodifiable()) {
                if (cell.getLayoutX() <= x && x <= (cell.getLayoutX() + 144.0)) {
                    if (cell.getParent().getLayoutY() <= y && y <= (cell.getParent().getLayoutY() + 180.0)) {
                        if(cell.getId()!=null) {
                            System.out.println("NOW ISLAND: " + cell.getId());
                            System.out.println(x);
                            System.out.println(y);

                            //CliViewIdConverter converter. = new CliViewIdConverter(getClientGui().getGuiView().getGameState());
                            //UUID islandId = converter.nameToId(cell.getId(), CliViewIdConverter.ConverterSetting.ISLAND);
                            UUID islandId = getClientGui().getGuiView().getInitialStateConverter().nameToId(cell.getId(), ClientNameIdConverter.ConverterSetting.ISLAND);

                            sendToServerAndUpdate(color, islandId);
                        }
                    }

                }
            }
        }



        for(Rectangle r1 : diningTables) {
            //  if( r1.getLayoutY() <= y ) System.out.println(" \n" + r1.getParent().getLayoutY() + " \n" + r1.getParent().getLayoutX() + " \n" + r1.getLayoutY() );
            //if(r1.getId()!=null) { - use parse to recognize only rectangle with a "dining" in name (to differentiate to
            //entrace, professor and towers)
            if (y >= r1.getParent().getLayoutY() && y <= (r1.getParent().getLayoutY() + 50)) {
                System.out.println("green table");
                sendToServerAndUpdate(color, null);
                break;

                //add method to count number of pawns in rectangle for island, in row for dining
                //for cycle should suffice
            } else if (y > (r1.getParent().getLayoutY() + 50) && y <= (r1.getParent().getLayoutY() + 80)) {
                System.out.println("red table");
                sendToServerAndUpdate(color, null);
                break;

            } else if (y > (r1.getParent().getLayoutY() + 80) && y <= (r1.getParent().getLayoutY() + 150)) {
                System.out.println("yellow table");
                sendToServerAndUpdate(color, null);
                break;


            } else if (y > (r1.getParent().getLayoutY() + 150) && y <= (r1.getParent().getLayoutY() + 190)) {
                System.out.println("purple table");
                sendToServerAndUpdate(color, null);
                break;


            } else if (y > (r1.getParent().getLayoutY() + 190) && y < (r1.getParent().getLayoutY() + 240)) {
                System.out.println("blue table");
                sendToServerAndUpdate(color, null);
                break;
            }


        }


    }

    private void sendToServerAndUpdate(PawnColor color, UUID islandId) throws IOException {
        UUID currentState = getClientGui().getGuiView().getGameState().getId();
        System.out.println("color: " + color);
        //when color is null it means it is mother nature that has been moved
        if (color == null){
            if (gameBoardState.getBoardState()!= GameBoardState.BoardState.MOVING_MN){
                System.out.println("illegal MN move");
                return;
            }
            System.out.println("sending MN steps to server");
            GameState gameState = getClientGui().getGuiView().getGameState();
            UUID motherNaturePosition = gameState.getMotherNaturePosition();
            ArrayList<UUID> islands = new ArrayList<>(gameState.getIslands().keySet());
            int steps = islands.indexOf(islandId) - islands.indexOf(motherNaturePosition);
            if (steps<0) steps = islands.size() + steps;
            if (steps<1 || steps>5){
                System.out.println("illegal MN move");
                return;
            }
            getClientGui().getGuiView().notifyEventManager(new MovedMotherNature(steps));
            gameBoardState.nextState();
        } else {
            if (gameBoardState.getBoardState()!= GameBoardState.BoardState.MOVING_STUDENT){
                System.out.println("illegal student move");
                return;
            }
            System.out.println("sending student move to server");
            getClientGui().getGuiView().notifyEventManager(new MovedStudent(color, islandId));
            gameBoardState.nextState();
        }

        new ChangeScene(getClientGui()).run();
    }

    public void clickedCloud1() throws IOException {
        if (gameBoardState.getBoardState()!= GameBoardState.BoardState.CHOOSING_CLOUD){
            System.out.println("illegal cloud move");
            return;
        }
        Iterator<UUID> iterator = getClientGui().getGuiView().getGameState().getClouds().keySet().iterator();
        UUID cloudId = iterator.next();
        System.out.println("sending to server chosen cloud 1");
        getClientGui().getGuiView().notifyEventManager(new ChosenCloud(cloudId));
        gameBoardState.nextState();
        getClientGui().getGuiView().changeSceneToGameBoard(false, true);
    }

    public void clickedCloud2() throws IOException {
        if (gameBoardState.getBoardState()!= GameBoardState.BoardState.CHOOSING_CLOUD){
            System.out.println("illegal cloud move");
            return;
        }
        Iterator<UUID> iterator = getClientGui().getGuiView().getGameState().getClouds().keySet().iterator();
        iterator.next();
        UUID cloudId = iterator.next();
        System.out.println("sending to server chosen cloud 2");
        getClientGui().getGuiView().notifyEventManager(new ChosenCloud(cloudId));
        gameBoardState.nextState();
        getClientGui().getGuiView().changeSceneToGameBoard(false, true);
    }

    public void clickedCloud3() throws IOException {
        if (gameBoardState.getBoardState()!= GameBoardState.BoardState.CHOOSING_CLOUD){
            System.out.println("illegal cloud move");
            return;
        }
        Iterator<UUID> iterator = getClientGui().getGuiView().getGameState().getClouds().keySet().iterator();
        iterator.next();
        iterator.next();
        UUID cloudId = iterator.next();
        System.out.println("sending to server chosen cloud 3");
        getClientGui().getGuiView().notifyEventManager(new ChosenCloud(cloudId));
        gameBoardState.nextState();
        getClientGui().getGuiView().changeSceneToGameBoard(false, true);
    }

}
