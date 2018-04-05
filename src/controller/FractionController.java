package controller;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class FractionController extends VBox {
    @FXML
    private TextField textField;

    public FractionController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/FractionView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) { textProperty().set(value); }


    public StringProperty textProperty() {
        return textField.textProperty();
    }

    public void bindProperty(Property<String> value) {
        textField.textProperty().bindBidirectional(value);
    }

    public StringProperty textProperty(Property<String> value) {
        return textField.textProperty();
    }

    @FXML
    protected void doSomething() throws InterruptedException {
        System.out.println("The button was clicked!");
        wait();
    }

    @FXML
    protected void doSomethingElse() {
        System.out.println("The button was clicked!");
    }
}
