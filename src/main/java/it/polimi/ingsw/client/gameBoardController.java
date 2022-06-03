package it.polimi.ingsw.client;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class gameBoardController {

    @FXML
    VBox vbox;
    @FXML
    Pane schoolBoard;
    @FXML
    Circle circle;
    @FXML
    Group rectangleGroup;
    @FXML
    Group squaresGroup;

    @FXML
    Pane firstRow;
    @FXML
    Rectangle tile01;
    @FXML
    Rectangle tile02;

    private final List<Pane> panes = new ArrayList<>();

    @FXML
    public void initialize(){
        panes.add(firstRow);

        schoolBoard.addEventFilter(MouseEvent.MOUSE_EXITED, this::leaveBoard);
        schoolBoard.addEventFilter(MouseEvent.MOUSE_RELEASED, this::checkReleaseOutOfBoard);

        vbox.setMaxWidth(1556.0d);
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
        Point2D panePt = schoolBoard.sceneToLocal(pt);
        return (panePt.getX()-offset.getX() >=0.0d
                && panePt.getY()-offset.getY() >= 0.0d
                && panePt.getX() <= schoolBoard.getWidth()
                && panePt.getY() <= schoolBoard.getHeight());
    }

    public void finishMovingPiece(MouseEvent evt){
        offset = new Point2D(0.0d, 0.0d);

        Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
        Point2D mousePointScene = circle.localToScene(mousePoint);

        Rectangle r = pickRectangle( mousePointScene.getX(), mousePointScene.getY());

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);

        if(r!=null){
            Point2D rectScene = r.localToScene(r.getX(), r.getY());
            Point2D parent = schoolBoard.sceneToLocal(rectScene.getX(), rectScene.getY());

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
