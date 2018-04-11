package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.BoardElement;
/**
 * Controller für FractionView.fxml
 * @author  Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0 30.03.2018
 */
import java.io.IOException;

public class FractionController extends VBox {
    @FXML
    private Label lblFraction;
    private BoardElement boardElement;

    public FractionController(BoardElement boardElement) {
        this.boardElement = boardElement;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/FractionView.fxml"));
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
