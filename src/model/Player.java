package model;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.*;

/**
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 3.0 19/04/2018
 */

public class Player implements Serializable{
    // Name
    Position position;
    transient private StringProperty name = new SimpleStringProperty();
    transient private BooleanProperty isSelectedProperty = new SimpleBooleanProperty();
    transient private ObjectProperty<Fraction> score = new SimpleObjectProperty<>(new Fraction(0,1));
    transient private StringProperty shortName = new SimpleStringProperty();
    transient public ObjectProperty<Paint> fillProperty = new SimpleObjectProperty<>();

    Player (Position position, String name, String shortName, Color color) {
        this.position = position;
        setName(name);
        setShortName(shortName);
        setFillProperty(color);
    }

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
    public Boolean isSelected() { return isSelectedProperty.get(); }
    public BooleanProperty isSelectedProperty() { return isSelectedProperty; }
    public void setIsSelectedProperty(Boolean selected) { isSelectedProperty.set(selected); }

    // Fill
    public Paint getFill() { return fillProperty.get(); }
    public ObjectProperty<Paint> getFillProperty() {
        return fillProperty;
    }
    public void setFillProperty(Color color) {
        fillProperty.set(color);
    }




    Player (Position position) {
        this.position = position;
    }


    public Position getPosition() {
        return position;
    }

    //Bewegt den Spieler
    public Player moveDirection(Actions direction) {
        position.moveDirection(direction);
        return this;
    }

    //gibt position des Spielers nach einer Bewegung
    public Player peekDirection(Actions direction) {
        return new Player(position.peekDirection(direction));
    }

    @Override
    public String toString() {
        return this.getShortName();
    }


}
