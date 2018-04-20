package Board;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Paint;

/**
 * Boardelemnte
 * @author  Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0 30.03.2018
 */
public class BoardElement {
    private StringProperty text = new SimpleStringProperty();
    public StringProperty getTextProperty() {
        return text;
    }
    public void setTextProperty(String value) {
        text.set(value);
    }

    public ObjectProperty<Paint> fillProperty = new SimpleObjectProperty<>();
    public ObjectProperty<Paint> getFillProperty() {
        return fillProperty;
    }
    public void setFillProperty(Paint color) {
        fillProperty.set(color);
    }
}
