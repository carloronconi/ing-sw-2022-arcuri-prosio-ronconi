package it.polimi.ingsw.client;

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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class GameBoardController {
    @FXML
    Pane boardPane;

    @FXML
    Group rectangleGroup;

    @FXML
    VBox vbox;

    @FXML
    Circle circle;

    @FXML Pane islandRow0;
    @FXML Pane islandRow1;
    @FXML Pane islandRow2;
    @FXML Pane school0;
    @FXML Pane school1;
    @FXML Pane school2;
    @FXML Pane school3;
    @FXML Pane school4;


    private final List<Pane> panes = new ArrayList<>();

    @FXML
    public void initialize(){
        panes.add(islandRow0);
        panes.add(islandRow1);
        panes.add(islandRow2);
        panes.add(school0);
        panes.add(school1);
        panes.add(school2);
        panes.add(school3);
        panes.add(school4);

        boardPane.addEventFilter(MouseEvent.MOUSE_EXITED, this::leaveBoard);
        boardPane.addEventFilter(MouseEvent.MOUSE_RELEASED, this::checkReleaseOutOfBoard);

        vbox.setMaxWidth(1500.0d);

        islands.add(island1);
        islands.add(island2);
        islands.add(island3);
        islands.add(island4);
        islands.add(island5);
        islands.add(island6);
        islands.add(island7);
        islands.add(island8);
        islands.add(island9);
        islands.add(island10);
        islands.add(island11);
        islands.add(island12);
    }

    private Rectangle currRect;

    private Point2D offset = new Point2D(0.0d, 0.0d);
    private boolean movingPiece = false;

    public void checkReleaseOutOfBoard(MouseEvent evt){
        Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());
        if(!inBoard(mousePoint_s)){
            leaveBoard(evt);
            evt.consume();
        }

    }

    public void leaveBoard(MouseEvent evt){
        if(movingPiece){
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

    @FXML
    public void startMovingPiece(MouseEvent evt){
        circle.setOpacity(0.4d);
        offset = new Point2D(evt.getX(), evt.getY());

        movingPiece=true;
    }

    @FXML
    public void movePiece(MouseEvent evt){

        Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
        Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());

        if(!inBoard(mousePoint_s)){
            return;
        }

        Point2D mousePoint_p = circle.localToParent(mousePoint);
        circle.relocate(mousePoint_p.getX()-offset.getX(), mousePoint_p.getY()-offset.getY());

    }

    private boolean inBoard(Point2D pt){
        Point2D panePt = boardPane.sceneToLocal(pt);
        //Point2D panePtIsle = boardPane.sceneToLocal(pt);
        return (panePt.getX()-offset.getX() >=0.0d
                && panePt.getY()-offset.getY() >= 0.0d
                && panePt.getX() <= boardPane.getWidth()
                && panePt.getY() <= boardPane.getHeight());
    }

    public Rectangle finishMovingPiece(MouseEvent evt){
        offset = new Point2D(0.0d, 0.0d);

        Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
        Point2D mousePointScene = circle.localToScene(mousePoint);

        Rectangle r = pickRectangle( mousePointScene.getX(), mousePointScene.getY());

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);

        if(r!=null){
            Point2D rectScene = r.localToScene(r.getX(), r.getY());
            Point2D parent = boardPane.sceneToLocal(rectScene.getX(), rectScene.getY());

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100),
                            new KeyValue(circle.layoutXProperty(), parent.getX()),
                            new KeyValue(circle.layoutYProperty(), parent.getY()),
                            new KeyValue(circle.opacityProperty(),1.0d))
            );

        }else{
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100),
                            new KeyValue(circle.opacityProperty(), 1.0d))
            );
        }
        timeline.play();

        movingPiece=false;

        printWhere(circle.getLayoutX(), circle.getLayoutY());

        return currRect;

    }

    @FXML Rectangle island1;
    @FXML Rectangle island2;
    @FXML Rectangle island3;
    @FXML Rectangle island4;
    @FXML Rectangle island5;
    @FXML Rectangle island6;
    @FXML Rectangle island7;
    @FXML Rectangle island8;
    @FXML Rectangle island9;
    @FXML Rectangle island10;
    @FXML Rectangle island11;
    @FXML Rectangle island12;
    @FXML Rectangle cloud1;
    @FXML Rectangle cloud2;
    @FXML Rectangle bag;


    private final List<Rectangle> islands = new ArrayList<>();

    public void print(){
            double circX = circle.getLayoutX();
            double circY = circle.getLayoutY();




             //System.out.println(" "+ isle.getLayoutY());
            // System.out.println("circle: " + circle.getLayoutY());

       // if(circle.getLayoutX() >= sq3_2.getLayoutX()) System.out.println("new place: " +sq3_2.getId());
            //System.out.println("New coordinates:" + circle.getLayoutX());
            //System.out.println("\n sq3 coordinates: " + sq3_2.getLayoutX());
    }

    public void printWhere(Double x, Double y) {
        for (Rectangle rect : islands) {
            if (rect.getLayoutX() <= x && x <= (rect.getLayoutX() + rect.getWidth())) {
                if (rect.getParent().getLayoutY() <= y && y < (rect.getParent().getLayoutY() + rect.getHeight())) {
                    System.out.println("ISLAND: " + rect.getId() + " " + rect.getLayoutX() + " " + rect.getParent().getLayoutY() + " " + x + " " + y);
                }
            }
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
        for(Pane row : panes){
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
