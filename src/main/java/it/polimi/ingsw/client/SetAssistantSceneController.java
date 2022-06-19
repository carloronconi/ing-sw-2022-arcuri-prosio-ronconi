package it.polimi.ingsw.client;

import it.polimi.ingsw.networkmessages.viewevents.SetAssistantCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetAssistantSceneController extends SceneController {
    int cardNumber;
    @FXML
    Button button;
    @FXML
    ImageView card1;
    @FXML ImageView card2;
    @FXML ImageView card3;
    @FXML ImageView card4;
    @FXML ImageView card5;
    @FXML ImageView card6;
    @FXML ImageView card7;
    @FXML ImageView card8;
    @FXML ImageView card9;
    @FXML ImageView card10;
    @FXML
    private ImageView lastPlayed1;
    @FXML
    private ImageView playedByOther;
    static ArrayList<String> playedByOtherResources = new ArrayList<>();

    public ImageView getPlayedByOther() {
        return playedByOther;
    }

    public void clickedButton(ActionEvent e) throws IOException { //button at the end of set assistant card scene
        getClientGui().getGuiView().notifyEventManager(new SetAssistantCard(cardNumber));
        getClientGui().nextScene(1500, 876, "ERYANTIS", (s, c)->{
            GameBoardController boardController = (GameBoardController) c;
            boardController.updateBoard(getClientGui().getGuiView().getGameState());
        });


    }

    public void chosenCard1(){
        number(card1);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(1)-min.png"))));
        card1.setOpacity(0.4d);
    }
    public void chosenCard2(){
        number(card2);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(2)-min.png"))));
        card2.setOpacity(0.4d);
    }
    public void chosenCard3(){
        number(card3);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(3)-min.png"))));
        card3.setOpacity(0.4d);
    }
    public void chosenCard4(){
        number(card4);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(4)-min.png"))));
        card4.setOpacity(0.4d);
    }
    public void chosenCard5(){
        number(card5);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(5)-min.png"))));
        card5.setOpacity(0.4d);
    }
    public void chosenCard6(){
        number(card6);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(6)-min.png"))));
        card6.setOpacity(0.4d);
    }
    public void chosenCard7(){
        number(card7);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(7)-min.png"))));
        card7.setOpacity(0.4d);
    }
    public void chosenCard8(){
        number(card8);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(8)-min.png"))));
        card8.setOpacity(0.4d);
    }
    public void chosenCard9(){
        number(card9);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(9)-min.png"))));
        card9.setOpacity(0.4d);
    }
    public void chosenCard10(){
        number(card10);
        lastPlayed1.setImage(new Image(String.valueOf(getClass().getResource("/Assistente(10)-min.png"))));
        card10.setOpacity(0.4d);
    }

    private void number(ImageView imageView) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(imageView.toString());
        while (m.find()) {
            cardNumber = Integer.parseInt(m.group());
            System.out.println(cardNumber);

        }

    }
}
