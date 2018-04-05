package model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BoardElement {
    private StringProperty text = new SimpleStringProperty();
    public StringProperty getTextProperty() {
        return text;
    }
    public void setTextProperty(String value) {
        text.set(value);
    }
}
