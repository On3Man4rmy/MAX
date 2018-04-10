package model;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EndGame {
    String spielstand;
    EndGame(String spielstand){
        this.spielstand=spielstand;
    }
    public void endgame(){
        Stage stage = new Stage();
        Pane pane = new Pane();
        pane.setPadding(new Insets(5, 5, 5, 5));
        Text text1 = new Text(20, 20,spielstand);
        text1.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
        pane.getChildren().add(text1);
        Scene scene = new Scene(pane, 400, 500);
        stage.setScene(scene);
        stage.setTitle("Endstand");
        stage.show();


    }

}
