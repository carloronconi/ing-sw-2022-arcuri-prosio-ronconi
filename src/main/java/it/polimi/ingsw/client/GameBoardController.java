package it.polimi.ingsw.client;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class GameBoardController extends SceneController{
    @FXML
    Pane boardPane;
    @FXML
    Group rectangleGroup;
    @FXML
    VBox vbox;
    @FXML
    Circle circle;
    @FXML
    Pane islandRow0;
    @FXML
    Pane islandRow1;
    @FXML
    Pane islandRow2;

    @FXML
    GridPane entrance1;
    @FXML
    GridPane dinings;
    @FXML
    GridPane professors;
    @FXML
    GridPane towers;


    private final List<Pane> islands = new ArrayList<>();
    private final List<GridPane> board = new ArrayList<>();
    private final int MAX_SIZE = 130;
    private ArrayList<Circle> bag = new ArrayList<>(MAX_SIZE);



    @FXML
    public void initialize() {
        islands.add(islandRow0);
        islands.add(islandRow1);
        islands.add(islandRow2);
        //board.add(entrance1);
        board.add(dinings);
        //board.add(professors);
        // board.add(towers);

        boardPane.addEventFilter(MouseEvent.MOUSE_EXITED, this::leaveBoard);
        boardPane.addEventFilter(MouseEvent.MOUSE_RELEASED, this::checkReleaseOutOfBoard);

        vbox.setMaxWidth(1500.0d);

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

    private Circle makeCircle(PawnColor color){
        Circle circle = new Circle();
        circle.setFill(Paint.valueOf(color.name()));
        return circle;
    }

    public void updateBoard(GameState gameState){ //call it from lambda expression in previous controller nextScene
        //fill islands with same pawns as in gameState
        for (Pane pane : islands){
            for (Node island : pane.getChildrenUnmodifiable()) {
                String islandId = island.getId();
                if (islandId==null || islandId.contains("bag") || islandId.contains("cloud")) continue;
                //TODO: use makeCircle to create student with color based on gameState and add the circle to the island
                Circle student = makeCircle(PawnColor.RED);

            }
        }
    }

    private Ellipse motherNature;

    //TODO: set random

        private void initializeBoard(){
          for(Pane p : islands) {
              for(Node rect : p.getChildrenUnmodifiable()) {
                  if(rect.getId()!=null && !rect.getId().toString().contains("12") && !rect.getId().toString().contains("6") &&
                          !rect.getId().toString().contains("bag") && !rect.getId().toString().contains("cloud")
                  ) {
                      Circle c = bag.get(0);
                      c.setCenterX(50.0);
                      c.setCenterY(50.0);
                      c.setLayoutX(rect.getLayoutX());
                      c.setLayoutY(rect.getParent().getLayoutY());
                      c.setRadius(16.0);
                      c.setStroke(Color.BLACK);
                      c.setStrokeType(StrokeType.INSIDE);

                      rectangleGroup.getChildren().add(c);
                      pawns.add(c);
                      bag.remove(c);
                  }
              }
          }

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

    public void checkReleaseOutOfBoard(MouseEvent evt) {
        Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());
        if (!inBoard(mousePoint_s)) {
            leaveBoard(evt);
            evt.consume();
        }

    }

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

                if (!inBoard(mousePoint_s)) {
                    return;
                }

                Point2D mousePoint_p = circle.localToParent(mousePoint);
                circle.relocate(mousePoint_p.getX() - offset.getX(), mousePoint_p.getY() - offset.getY());

            }
    }

    private boolean inBoard(Point2D pt) {

            Point2D panePt = boardPane.sceneToLocal(pt);
            //Point2D panePtIsle = boardPane.sceneToLocal(pt);
            return (panePt.getX() - offset.getX() >= 0.0d
                    && panePt.getY() - offset.getY() >= 0.0d
                    && panePt.getX() <= boardPane.getWidth()
                    && panePt.getY() <= boardPane.getHeight());

    }

    public Rectangle finishMovingPiece(MouseEvent evt) {

        if(pawns.contains(evt.getSource())) {

            Circle circle = (Circle) evt.getSource();
            offset = new Point2D(0.0d, 0.0d);

            Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
            Point2D mousePointScene = circle.localToScene(mousePoint);

            Rectangle r = pickRectangle(mousePointScene.getX(), mousePointScene.getY());

            final Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);

            if (r != null) {
                Point2D rectScene = r.localToScene(r.getX(), r.getY());
                Point2D parent = boardPane.sceneToLocal(rectScene.getX(), rectScene.getY());

                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(100),
                                new KeyValue(circle.layoutXProperty(), parent.getX()),
                                new KeyValue(circle.layoutYProperty(), parent.getY()),
                                new KeyValue(circle.opacityProperty(), 1.0d))
                );

            } else {
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(100),
                                new KeyValue(circle.opacityProperty(), 1.0d))
                );
            }
            timeline.play();

            movingPiece = false;

            printWhere(circle.getLayoutX(), circle.getLayoutY());

        }


        return currRect;


    }

    @FXML
    Rectangle island1;
    @FXML
    Rectangle island2;
    @FXML
    Rectangle island3;
    @FXML
    Rectangle island4;
    @FXML
    Rectangle island5;
    @FXML
    Rectangle island6;
    @FXML
    Rectangle island7;
    @FXML
    Rectangle island8;
    @FXML
    Rectangle island9;
    @FXML
    Rectangle island10;
    @FXML
    Rectangle island11;
    @FXML
    Rectangle island12;
    @FXML
    Rectangle cloud1;
    @FXML
    Rectangle cloud2;
    @FXML
    Rectangle bag1;


    //private final List<Rectangle> islands = new ArrayList<>();

    private ArrayList<Circle> pawns = new ArrayList<>();
    private Circle c;

    //implementation swap pieces for update - diminish bag and update

    public void updateView() {
        Circle c = bag.get(15);
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
        System.out.println(bag.size());


    }
    int numOfRed =0;
    int numOfGreen =0;
    int numOfYellow =0;
    int numOfBlue =0;
    int numOfPurple =0;

    public void printWhere(Double x, Double y) {
        for (Pane p : islands) {
            for (Node cell : p.getChildrenUnmodifiable()) {
                if (cell.getLayoutX() <= x && x <= (cell.getLayoutX() + 144.0)) {
                    if (cell.getParent().getLayoutY() <= y && y <= (cell.getParent().getLayoutY() + 180.0)) {
                        System.out.println("NOW ISLAND: " + cell.getId());
                    }

                    //for(Node cell2 : panes.get(4).getChildrenUnmodifiable()){
                    //     System.out.println(" "+cell2.getId());
                       /*if(cell.getLayoutY() == cell2.getParent().getLayoutY()){
                           System.out.println("NOW DINING: " + cell2.getId());*/
                }
            }
        }
        for (GridPane p2 : board) {
            System.out.println(" " + p2.getRowCount() + " " + p2.getColumnCount());
            System.out.println(" " + y);

            int a =0;



            if (y >= p2.getLayoutY() && y <= (p2.getLayoutY() + 60)) {
                System.out.println("green");
                a = 1;
                //add method to count number of pawns in rectangle for island, in row for dining
                //for cycle should suffice
            }else if (y > (p2.getLayoutY() + 60) && y <= (p2.getLayoutY() + 100)) {
                System.out.println(" red ");
                a=2;
            }else if (y > (p2.getLayoutY() + 100) && y <= (p2.getLayoutY() + 140)) {
                System.out.println("yellow");
                a=3;
            }else if(y>(p2.getLayoutY()+140)&&y<=(p2.getLayoutY()+180)){
                System.out.println("purple");
                a=4;
            } else if(y> (p2.getLayoutY()+180) && y<(p2.getLayoutY()+300)){
                System.out.println("blue");
                a=5;
            }

                switch(a){
                    case 1: numOfGreen++; break;
                    case 2: numOfRed++; break;
                    case 3: numOfYellow++; break;
                    case 4: numOfPurple++; break;
                    case 5: numOfBlue++; break;

                }
                System.out.println(" "+numOfRed); //numOf
            }

            }


    public void mouseMoved(MouseEvent evt){
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


    }

    private Rectangle pickRectangle(MouseEvent evt){
        return pickRectangle(evt.getSceneX(), evt.getSceneY());
    }

    private Rectangle pickRectangle(double sceneX, double sceneY){
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
    }



    /*TODO: finish adding an id to remaining gridPanes (the schools) and add Students to entrance of a board. Try to set movements of those
    students (aka entrance to islands and entrance to dining.
    For now no game rules, just allowed movements.
     */




}
