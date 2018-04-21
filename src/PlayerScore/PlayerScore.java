package PlayerScore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Player;
import util.MathUtil;

/**
 * Controller für PlayerScore.fxml
 * @author  Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0 30.03.2018
 */
import java.io.IOException;

/**
 * Controller für anzeige der Scores
 *@author  Tobias Fetzer 198318, Simon Stratemeier 199067
 *@version 1.0 19/04/2018
 */
public class PlayerScore extends HBox {
    @FXML
    private Label lblPlayerName;
    @FXML
    private Label lblPlayerScore;
    @FXML
    private Label lblSelected;

    public PlayerScore() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayerScoreView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void bindPlayer(Player player) {
        lblPlayerName.textProperty().bind(player.getNameProperty());
        lblPlayerName.textFillProperty().bind(player.getFillProperty());
        lblPlayerScore.setText(String.valueOf(MathUtil.roundToDecimalPlaces(player.getScore().doubleValue(), 2)));
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
}
