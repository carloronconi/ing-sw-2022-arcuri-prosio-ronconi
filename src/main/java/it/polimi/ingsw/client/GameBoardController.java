package it.polimi.ingsw.client;

import it.polimi.ingsw.model.ConverterUtility;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.networkmessages.modelevents.GameState;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.util.*;


public class GameBoardController {
    @FXML Pane boardPane;
    @FXML Group rectangleGroup;
    @FXML VBox vbox;
    @FXML Circle circle;
    @FXML Pane islandRow0;
    @FXML Pane islandRow1;
    @FXML Pane islandRow2;
    @FXML Pane entrance1;
    @FXML Pane dinings1;
    @FXML Pane professors1;
    @FXML Pane towers1;
    @FXML Pane entrance2;
    @FXML Pane dinings2;
    @FXML Pane professors2;
    @FXML Pane towers2;


    private final List<Pane> islands = new ArrayList<>();
    private final List<Pane> board = new ArrayList<>();
    private final int MAX_SIZE = 130;
    private final ArrayList<Circle> bag = new ArrayList<>(MAX_SIZE);
    private final List<Pane> entr = new ArrayList<>();

    //private ArrayList<Integer> numOfPawnsInDining;
    private HashMap<String, Integer> pawnsInDining;

    String red;
    String yellow;
    String blue;
    String green;
    String purple;

    private List<Pane> panes = new ArrayList<>();




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

        entr.add(entrance1);
        entr.add(entrance2);
        islands.add(islandRow0);
        islands.add(islandRow1);
        islands.add(islandRow2);
        //board.add(entrance1);
        board.add(dinings1);

        panes.add(islandRow0);
        panes.add(islandRow1);
        panes.add(islandRow2);
        panes.add(entrance1);
        panes.add(dinings1);

        //board.add(professors);
        // board.add(towers);

        // boardPane.addEventFilter(MouseEvent.MOUSE_EXITED, this::leaveBoard);
        // boardPane.addEventFilter(MouseEvent.MOUSE_RELEASED, this::checkReleaseOutOfBoard);

        vbox.setMaxWidth(1500.0d);

        diningTables.add(green1);
        diningTables.add(red1);
        diningTables.add(yellow1);
        diningTables.add(purple1);
        diningTables.add(blue1);

        pawns.add(circle);
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

        //initializeBoard();
        //TODO: initialization: 10 students from bag to the island tiles, seven student in entrances - towers and professors also
        //take a random circle from bag and put in the coordinates of the island tile / of the entrance


    }

    public void updateBoard(GameState gameState){ //call it from lambda expression in previous controller nextScene

        int bag = gameState.getBag();
        CliViewIdConverter converter = new CliViewIdConverter(gameState);

        //ISLANDS

        for (Pane p : islands){
            for (Node r: p.getChildrenUnmodifiable()){
                for (int i = 0; i<gameState.getIslands().size(); i++){
                    int islandNumber = i+1;
                    String islandName = "island" + islandNumber;
                    if (r.getId()!=null && r.getId().contains(islandName)){
                        LinkedHashMap<UUID, ArrayList<PawnColor>> islandIGameModel = gameState.getIslands();
                        UUID islandId = converter.nameToId(islandName, CliViewIdConverter.converterSetting.ISLAND);
                        for(int j = 0; j<islandIGameModel.get(islandId).size(); j++){
                            Circle c = new Circle();
                            c.setCenterX(50.0);
                            c.setCenterY(50.0);
                            c.setLayoutX(r.getLayoutX());
                            c.setLayoutY(r.getParent().getLayoutY());
                            c.setRadius(16.0);
                            c.setStroke(Color.BLACK);
                            c.setStrokeType(StrokeType.INSIDE);
                            c.setFill(Color.valueOf(islandIGameModel.get(islandId).get(j).toString()));

                            boardPane.getChildren().add(c);

                        }
                    }
                }
            }
        }

        //CLOUDS

        for(Pane p : islands){
            for(Node r: p.getChildrenUnmodifiable()){
                for(int i=0; i<gameState.getClouds().size(); i++){
                    int cloudNumber = i+1;
                    String cloudName = "cloud" + cloudNumber;
                    if(r.getId()!=null && r.getId().contains(cloudName)){
                        HashMap<UUID, ArrayList<PawnColor>> cloudIGameModel = gameState.getClouds();
                        UUID cloudID = converter.nameToId(cloudName, CliViewIdConverter.converterSetting.CLOUD);
                        for(int j = 0; j<cloudIGameModel.get(cloudID).toArray().length; j++){
                                Circle c = new Circle();
                                c.setCenterX(50.0);
                                c.setCenterY(50.0);
                                c.setLayoutX(r.getLayoutX()+(j*30.0));
                                c.setLayoutY(r.getParent().getLayoutY()+(j*35.0));
                                c.setRadius(16.0);
                                c.setStroke(Color.BLACK);
                                c.setStrokeType(StrokeType.INSIDE);
                                c.setFill(Color.valueOf(cloudIGameModel.get(cloudID).get(j).toString()));

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

        //ENTRANCES
        for(Pane p : entr){
            for(Node rect : p.getChildrenUnmodifiable()){
                if(rect.getId()!=null){
                    //interleave with game state

                    /*Circle c = bag.get(index);
                    c.setCenterX(50.0);
                    c.setCenterY(50.0);
                    c.setLayoutX(rect.getLayoutX() - 15.0);
                    c.setLayoutY(rect.getLayoutY() + rect.getParent().getLayoutY() - 20.0);
                    c.setRadius(16.0);
                    c.setStroke(Color.BLACK);
                    c.setStrokeType(StrokeType.INSIDE);

                    c.setOnMouseDragged(this::movePiece);
                    c.setOnMousePressed(this::startMovingPiece);
                    c.setOnMouseReleased(this::finishMovingPiece);



                    boardPane.getChildren().add(c); //rectangleGroup
                    pawns.add(c);
                    bag.remove(c); */


                }
            }


        }


        /*
        //fill islands with same pawns as in gameState
        for (Pane pane : islands){
            for (Node island : pane.getChildrenUnmodifiable()) {
                String islandId = island.getId();
                if (islandId==null || islandId.contains("bag") || islandId.contains("cloud")) continue;
                //TODO: use makeCircle to create student with color based on gameState and add the circle to the island
                Circle student = makeCircle(PawnColor.RED);

            }
        }*/
    }

    private final Ellipse motherNature = new Ellipse();

    //TODO: set random

    private void initializeBoard(){
        for(Pane p : islands) {
            for(Node rect : p.getChildrenUnmodifiable()) {
                if(rect.getId()!=null && !rect.getId().toString().contains("12") && !rect.getId().toString().contains("6") &&
                        !rect.getId().toString().contains("bag") && !rect.getId().toString().contains("cloud")
                ) {
                    Random random = new Random();
                    int index = random.nextInt(bag.size());
                    Circle c = bag.get(index);
                    c.setCenterX(50.0);
                    c.setCenterY(50.0);
                    c.setLayoutX(rect.getLayoutX());
                    c.setLayoutY(rect.getParent().getLayoutY());
                    c.setRadius(16.0);
                    c.setStroke(Color.BLACK);
                    c.setStrokeType(StrokeType.INSIDE);


                    boardPane.getChildren().add(c);  //rectangleGroup
                    pawns.add(c);
                    bag.remove(c);



                     /* Circle c = bag.get(0);
                      c.setCenterX(50.0);
                      c.setCenterY(50.0);
                      c.setLayoutX(rect.getLayoutX());
                      c.setLayoutY(rect.getParent().getLayoutY());
                      c.setRadius(16.0);
                      c.setStroke(Color.BLACK);
                      c.setStrokeType(StrokeType.INSIDE);

                      rectangleGroup.getChildren().add(c);
                      pawns.add(c);
                      bag.remove(c); */
                }



            }

        }

        System.out.println(bag.size());



         /* motherNature = new Ellipse(44.0,26.0);
          motherNature.setFill(Color.valueOf("#ff9d21"));
          motherNature.setStroke(Color.BLACK);
          motherNature.setStrokeType(StrokeType.INSIDE);
          motherNature.setLayoutX(island12.getLayoutX());
          motherNature.setLayoutY(island12.getParent().getLayoutY());
            motherNature.setOnMouseDragged(this::movePiece);
            motherNature.setOnMousePressed(this::startMovingPiece);
            motherNature.setOnMouseReleased(this::finishMovingPiece);
            rectangleGroup.getChildren().add(motherNature);
            */  //mother nature line in fxml


    }



    private Rectangle currRect;

    private Point2D offset = new Point2D(0.0d, 0.0d);
    private boolean movingPiece = false;

   /* public void checkReleaseOutOfBoard(MouseEvent evt) {
        Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());
        if (!inBoard(mousePoint_s)) {
            leaveBoard(evt);
            evt.consume();
        }

    } */

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

               /* if (!inBoard(mousePoint_s)) {
                    return;
                }  */

            Point2D mousePoint_p = circle.localToParent(mousePoint);
            circle.relocate(mousePoint_p.getX() - offset.getX(), mousePoint_p.getY() - offset.getY());

        }
    }

   /* private boolean inBoard(Point2D pt) {



            Point2D panePt = boardPane.sceneToLocal(pt); //boardPane
            //Point2D panePtIsle = boardPane.sceneToLocal(pt);
            return (panePt.getX() - offset.getX() >= 0.0d
                    && panePt.getY() - offset.getY() >= 0.0d
                    && panePt.getX() <= boardPane.getWidth()
                    && panePt.getY() <= boardPane.getHeight());

    } */

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



            //Rectangle r = pickRectangle(mousePointScene.getX(), mousePointScene.getY());



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


            printWhere(circle.getLayoutX(), circle.getLayoutY());

        }


        return currRect;


    }




    //private final List<Rectangle> islands = new ArrayList<>();

    private ArrayList<Circle> pawns = new ArrayList<>();


    //implementation swap pieces for update - diminish bag and update

    public void updateView() {

        for(Pane p : entr){
            for(Node rect : p.getChildrenUnmodifiable()){
                if(rect.getId()!=null){

                    Random rand = new Random();
                    int index = rand.nextInt(bag.size());
                    Circle c = bag.get(index);
                    c.setCenterX(50.0);
                    c.setCenterY(50.0);
                    c.setLayoutX(rect.getLayoutX() - 15.0);
                    c.setLayoutY(rect.getLayoutY() + rect.getParent().getLayoutY() - 20.0);
                    c.setRadius(16.0);
                    c.setStroke(Color.BLACK);
                    c.setStrokeType(StrokeType.INSIDE);

                    c.setOnMouseDragged(this::movePiece);
                    c.setOnMousePressed(this::startMovingPiece);
                    c.setOnMouseReleased(this::finishMovingPiece);



                    boardPane.getChildren().add(c); //rectangleGroup
                    pawns.add(c);
                    bag.remove(c);


                }
            }

            System.out.println(bag.size());
        }

        /*for(Node rect : entrance1.getChildrenUnmodifiable()) {

            for (int i = 0; i < 7; i++) {
                int index = rand.nextInt(bag.size());
                Circle circle = bag.get(index);
                circle.setCenterX(50.0);
                circle.setCenterY(50.0);
                circle.setLayoutX(rect.getLayoutX() + (rect.getBoundsInLocal().getCenterX()));
                circle.setLayoutY(rect.getParent().getLayoutY() + (rect.getBoundsInLocal().getCenterY()));
                circle.setRadius(16.0);
                circle.setStroke(Color.BLACK);
                circle.setStrokeType(StrokeType.INSIDE);
                circle.setOnMouseDragged(this::movePiece);
                circle.setOnMousePressed(this::startMovingPiece);
                circle.setOnMouseReleased(this::finishMovingPiece);
                rectangleGroup.getChildren().add(circle);
                pawns.add(circle);
                bag.remove(circle);

            }


        } */

       /* Circle c = bag.get(15);
        c.setCenterX(50.0);
        c.setCenterY(50.0);
        c.setLayoutX(entrance1.getLayoutX()+ (((entrance1.getLayoutX()+entrance1.getPrefWidth())/(entrance1.getColumnCount()))/2));
        c.setLayoutY(entrance1.getLayoutY()+ (((entrance1.getLayoutY()+entrance1.getPrefHeight())/(entrance1.getRowCount()))/2));
        c.setRadius(16.0);
        c.setStroke(Color.BLACK);
        c.setStrokeType(StrokeType.INSIDE);
        c.setOnMouseDragged(this::movePiece);
        c.setOnMousePressed(this::startMovingPiece);
        c.setOnMouseReleased(this::finishMovingPiece);

        rectangleGroup.getChildren().add(c);
        pawns.add(c);
        bag.remove(c);

        System.out.println(pawns.size());
        System.out.println(bag.size()); */


    }

    private ArrayList<Rectangle> diningTables = new ArrayList<>();
    @FXML Rectangle green1;
    @FXML Rectangle red1;
    @FXML Rectangle yellow1;
    @FXML Rectangle purple1;
    @FXML Rectangle blue1;

    public void printWhere(Double x, Double y) {
        for (Pane p : islands) {
            for (Node cell : p.getChildrenUnmodifiable()) {
                if (cell.getLayoutX() <= x && x <= (cell.getLayoutX() + 144.0)) {
                    if (cell.getParent().getLayoutY() <= y && y <= (cell.getParent().getLayoutY() + 180.0)) {
                        if(cell.getId()!=null) {
                            System.out.println("NOW ISLAND: " + cell.getId());
                            System.out.println(x);
                            System.out.println(y);
                        }
                    }

                    //for(Node cell2 : panes.get(4).getChildrenUnmodifiable()){
                    //     System.out.println(" "+cell2.getId());
                       /*if(cell.getLayoutY() == cell2.getParent().getLayoutY()){
                           System.out.println("NOW DINING: " + cell2.getId());*/
                }
            }
        }



        for(Rectangle r1 : diningTables) {
            //  if( r1.getLayoutY() <= y ) System.out.println(" \n" + r1.getParent().getLayoutY() + " \n" + r1.getParent().getLayoutX() + " \n" + r1.getLayoutY() );
            //if(r1.getId()!=null) { - use parse to recognize only rectangle with a "dining" in name (to differentiate to
            //entrace, professor and towers)
            if (y >= r1.getParent().getLayoutY() && y <= (r1.getParent().getLayoutY() + 50)) {
                System.out.println("green");
                break;

                //add method to count number of pawns in rectangle for island, in row for dining
                //for cycle should suffice
            } else if (y > (r1.getParent().getLayoutY() + 50) && y <= (r1.getParent().getLayoutY() + 80)) {
                System.out.println(" red ");
                break;

            } else if (y > (r1.getParent().getLayoutY() + 80) && y <= (r1.getParent().getLayoutY() + 150)) {
                System.out.println("yellow");
                break;


            } else if (y > (r1.getParent().getLayoutY() + 150) && y <= (r1.getParent().getLayoutY() + 190)) {
                System.out.println("purple");
                break;


            } else if (y > (r1.getParent().getLayoutY() + 190) && y < (r1.getParent().getLayoutY() + 240)) {
                System.out.println("blue");
                break;
            }
            // }

        }

        //System.out.println(" "+ r1.getParent().getLayoutY()+ " " + r1.getLayoutY() + " " + r1.getBoundsInLocal().getHeight());
        //System.out.println(" "+ r1.getBoundsInLocal()+" "+ r1.getId());
               /* if((r1.getParent().getLayoutY() + i*60.0) <= y && y<= (r1.getParent().getLayoutY()+r1.getLayoutY() + r1.getBoundsInLocal().getHeight())
                        && x<= r1.getParent().getLayoutX()){
                    System.out.println(r1.getId());

                } */


        //Rectangle r = (Rectangle) r1;
        //System.out.println(x);
        //System.out.println(y);
              /*  if((r.getParent().getLayoutY() + r.getLayoutY()) < y && y< (r.getParent().getLayoutY() + r.getLayoutY() + r.getHeight()) &&
                        r.getParent().getLayoutX() >= x ){
                    System.out.println(r.getId());
                } */


    }
         /*for (Pane p2 : board) {
            //System.out.println(" " + p2.getRowCount() + " " + p2.getColumnCount());
            System.out.println(" " + y);
            for(Node rect : p2.getChildrenUnmodifiable()){

               //Rectangle row = (Rectangle) rect;

                if(x >= (rect.getParent().getLayoutX()) && x<= (rect.getParent().getLayoutX()+407.0) &&
                       y >= (rect.getLayoutY() + rect.getParent().getLayoutY()) && y<=(rect.getLayoutY()+rect.getParent().getLayoutY()+ 70.0)) {
                   System.out.println(rect.getId().toString());
                   System.out.println(rect.getLayoutX());
                   System.out.println((x));
                   String diningColor = rect.getId();
                   for (int i = 0; i < pawnsInDining.size(); i++) {
                       if (pawnsInDining.containsKey(diningColor)) {
                           pawnsInDining.put(diningColor, pawnsInDining.get(diningColor) + 1);
                           System.out.println(pawnsInDining.toString());
                       }

                   }
               }



            /*   if (y >= p2.getLayoutY() && y <= (p2.getLayoutY() + 60)) {
                System.out.println("green");
                a = 1;
                //add method to count number of pawns in rectangle for island, in row for dining
                //for cycle should suffice
            } else if (y > (p2.getLayoutY() + 60) && y <= (p2.getLayoutY() + 100)) {
                System.out.println(" red ");
                a = 2;
            } else if (y > (p2.getLayoutY() + 100) && y <= (p2.getLayoutY() + 140)) {
                System.out.println("yellow");
                a = 3;
            } else if (y > (p2.getLayoutY() + 140) && y <= (p2.getLayoutY() + 180)) {
                System.out.println("purple");
                a = 4;
            } else if (y > (p2.getLayoutY() + 180) && y < (p2.getLayoutY() + 300)) {
                System.out.println("blue");
                a = 5;
            } */

           /* switch (a) {
                case 1:
                    numOfGreen++;
                    break;
                case 2:
                    numOfRed++;
                    break;
                case 3:
                    numOfYellow++;
                    break;
                case 4:
                    numOfPurple++;
                    break;
                case 5:
                    numOfBlue++;
                    break;

            }*/
    // System.out.println(" " + numOfRed); //numOf
    //  }
    //     } */




    /*public void mouseMoved(MouseEvent evt){
        Rectangle r = pickRectangle(evt);

        if(r==null) {
            if (currRect != null) {
                currRect.setEffect(null);
            }
            currRect = null;
            return;
        }

        if(r!=currRect){
            if(currRect==null){
             currRect.setEffect(null);}
        } currRect = r;
        if(currRect!=null){
            Shadow shadow = new Shadow();
            currRect.setEffect(shadow);
        }


    }  */

   /* private Rectangle pickRectangle(MouseEvent evt){
        return pickRectangle(evt.getSceneX(), evt.getSceneY());
    } */

   /* private Rectangle pickRectangle(double sceneX, double sceneY){
        Rectangle pickedRectangle = null;
        for(Pane row : islands){
            Point2D mousePoint = new Point2D(sceneX, sceneY);
            Point2D mplocal = row.sceneToLocal(mousePoint);

            if(row.contains(mplocal)){
                row.getId();

                for(Node cell : row.getChildrenUnmodifiable()){
                    Point2D mplocalCell = cell.sceneToLocal(mousePoint);

                    if(cell.contains(mplocalCell)){
                        cell.getId();
                    }
                    pickedRectangle = (Rectangle) cell;
                    break;
                }
            }
            break;
        }

        return pickedRectangle;
    } */



    /*TODO: finish adding an id to remaining gridPanes (the schools) and add Students to entrance of a board. Try to set movements of those
    students (aka entrance to islands and entrance to dining.
    For now no game rules, just allowed movements.
     */




}

