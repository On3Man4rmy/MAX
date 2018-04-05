package controller;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Fraction;

import java.io.IOException;

public class FractionController extends VBox {
    @FXML
    private Label lblFraction;
    private Fraction fraction;

    public FractionController(Fraction fraction) {
        this.fraction = fraction;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/FractionView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        alignmentProperty().set(Pos.CENTER);
        lblFraction.setText(fraction.toString());
    }


    public StringProperty textProperty() {
        return lblFraction.textProperty();
    }

    @FXML
    protected void doSomething() throws InterruptedException {
        System.out.println("The button was clicked!");
        wait();
    }
}
