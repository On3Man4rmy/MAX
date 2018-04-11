package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Player;
import util.MathUtil;

/**
 * Controller für PlayerScoreView.fxml
 * @author  Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0 30.03.2018
 */
import java.io.IOException;

public class PlayerScoreController extends HBox {
    @FXML
    private Label lblPlayerName;
    @FXML
    private Label lblPlayerScore;
    @FXML
    private Label lblSelected;

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
        lblSelected.textFillProperty().bind(player.getFillProperty());
        if(player.isSelected()) {
            lblSelected.setText("◄");
        } else {
            lblSelected.setText("");
        }
        player.isSelectedProperty().addListener((observable, oldValue, selected) -> {
            if(selected) {
                lblSelected.setText("◄");
            } else {
                lblSelected.setText("");
            }
        });
    }

    @FXML
    protected void doSomething() throws InterruptedException {
        System.out.println("The button was clicked!");
        wait();
    }
}
