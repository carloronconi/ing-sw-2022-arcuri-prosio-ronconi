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


public class chooseAssistantController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button button;

    public void clickedButton(ActionEvent e) throws IOException {

        gameBoardController c = new gameBoardController();

        root = FXMLLoader.load(getClass().getResource("/GameBoard2.fxml"));

        scene = new Scene(root, 1560, 900);
        scene.setOnMouseMoved((evt) -> c.mouseMoved(evt));
        scene.setOnMouseDragged((evt)->c.mouseMoved(evt));
        stage = new Stage();
        stage.setTitle("Game Board");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    ImageView card1;
    @FXML
    ImageView lastPlayed1;

    private int cardNumber;

    public void chosenCard(){
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(card1.toString());
        while(m.find()){
            cardNumber = Integer.parseInt(m.group());
            System.out.println(""+cardNumber);
        }
        Image image = new Image(String.valueOf(getClass().getResource("/Assistente(1)-min.png")));
        lastPlayed1.setImage(image);

        //TODO: somehow remove assistant card chosen
    }

}
