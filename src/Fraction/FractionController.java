package Fraction;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.BoardElement;
import java.io.IOException;

/**
 * Controller für FractionView.fxml
 *
 * @author Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0 30.03.2018
 */

public class FractionController extends VBox {
    @FXML
    private Label lblFraction;

    public FractionController(BoardElement boardElement) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FractionView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        alignmentProperty().set(Pos.CENTER);
        lblFraction.textProperty().bind(boardElement.getTextProperty());
        lblFraction.textFillProperty().bind(boardElement.getFillProperty());
    }
}
