package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Fraction;
import model.Player;
import util.MathUtil;

import java.io.IOException;

public class PlayerScoreController extends HBox {
    @FXML
    private Label lblPlayerName;
    @FXML
    private Label lblPlayerScore;

    public PlayerScoreController(Player player) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/PlayerScoreView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        lblPlayerName.textProperty().bind(player.getNameProperty());
        lblPlayerName.textFillProperty().bind(player.getFillProperty());
        player.getScoreProperty().addListener((observable, oldValue, newValue) -> {
            lblPlayerScore.setText(String.valueOf(MathUtil.roundToDecimalPlaces(newValue.doubleValue(), 2)));
        });
        lblPlayerScore.textFillProperty().bind(player.getFillProperty());
    }

    @FXML
    protected void doSomething() throws InterruptedException {
        System.out.println("The button was clicked!");
        wait();
    }
}
