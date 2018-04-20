package Board.Fraction;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import Board.BoardElement;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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

    public FractionController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FractionView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        alignmentProperty().set(Pos.CENTER);
    }

    public StringProperty getTextProperty() {
        return lblFraction.textProperty();
    }
    public ObjectProperty<Paint> getFillProperty() {
        return lblFraction.textFillProperty();
    }
}
