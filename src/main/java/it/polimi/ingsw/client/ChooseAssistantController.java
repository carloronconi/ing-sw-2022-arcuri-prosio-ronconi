package it.polimi.ingsw.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ChooseAssistantController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ClientGui clientGui;

    public ChooseAssistantController(ClientGui clientGui){
        this.clientGui = clientGui;



    }







    @FXML
    Button button;

    @FXML ImageView card1;
    @FXML ImageView card2;
    @FXML ImageView card3;
    @FXML ImageView card4;
    @FXML ImageView card5;
    @FXML ImageView card6;
    @FXML ImageView card7;
    @FXML ImageView card8;
    @FXML ImageView card9;
    @FXML ImageView card10;



    public void clickedButton(ActionEvent e) throws IOException {

        GameBoardController c = new GameBoardController();

        root = FXMLLoader.load(getClass().getResource("/GameBoard2.fxml"));

        scene = new Scene(root, 1500, 876);
        scene.setOnMouseMoved((evt) -> c.mouseMoved(evt));
        scene.setOnMouseDragged((evt)->c.mouseMoved(evt));
        stage = new Stage();
        stage.setTitle("Game Board");
        stage.setScene(scene);
        stage.show();


    }


    @FXML
    private ImageView lastPlayed1;


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

    private int cardNumber;

    private void number(ImageView imageView) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(imageView.toString());
        while (m.find()) {
            cardNumber = Integer.parseInt(m.group());
            System.out.println("" + cardNumber);
            clientGui.updateChosenAssistant(cardNumber);

        }

    }

    public int getCardNumber(){
        return cardNumber;
    }



}
