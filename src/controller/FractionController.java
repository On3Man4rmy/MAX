package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.BoardElement;

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

    @FXML
    protected void doSomething() throws InterruptedException {
        System.out.println("The button was clicked!");
        wait();
    }
}
