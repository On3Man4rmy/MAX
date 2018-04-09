package model;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 08.01.2018
 */

public class Player {
    // Name
    private StringProperty name = new SimpleStringProperty();
    public void setName(String name) {
        this.name.set(name);
    }
    public String getName() {
        return this.name.get();
    }
    public StringProperty getNameProperty() {
        return name;
    }
    // Short name
    private StringProperty shortName = new SimpleStringProperty();
    public void setShortName(String shortName) {
        this.shortName.set(shortName);
    }
    public String getShortName() {
        return this.shortName.get();
    }
    public StringProperty getShortNameProperty() {
        return shortName;
    }

    // Score
    private ObjectProperty<Fraction> score = new SimpleObjectProperty<>(new Fraction(0,1));
    public void setScore(Fraction score) {
        this.score.set(score);
    }
    public Fraction getScore() {
        return score.get();
    }
    public ObjectProperty<Fraction> getScoreProperty() {
        return score;
    }

    // Selected
    private BooleanProperty isSelectedProperty = new SimpleBooleanProperty();
    public Boolean isSelected() { return isSelectedProperty.get(); }
    public BooleanProperty isSelectedProperty() { return isSelectedProperty; }
    public void setIsSelectedProperty(Boolean selected) { isSelectedProperty.set(selected); }

    // Fill
    public ObjectProperty<Paint> fillProperty = new SimpleObjectProperty<>();
    public Paint getFill() { return fillProperty.get(); }
    public ObjectProperty<Paint> getFillProperty() {
        return fillProperty;
    }
    public void setFillProperty(Color color) {
        fillProperty.set(color);
    }

    Position position;

    Player (int posX, int posY) {
        this.position = new Position(posX, posY);
    }

    Player (Position position) {
        this.position = position;
    }

    Player (Position position, String name, String shortName) {
        this.position = position;
        setName(name);
        setShortName(shortName);
        setFillProperty(Color.GRAY);
    }

    Player (Position position, String name, String shortName, Color color) {
        this.position = position;
        setName(name);
        setShortName(shortName);
        setFillProperty(color);
    }

    public Player moveDirection(Direction direction) {
        position.moveDirection(direction);
        return this;
    }

    public Player peekDirection(Direction direction) {
        return new Player(position.peekDirection(direction));
    }

    @Override
    public String toString() {
        return this.getShortName();
    }
}
