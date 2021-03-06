package model;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.*;

/**
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 3.0 19/04/2018
 */

public class Player implements Serializable {
    // Name
    transient private ObjectProperty<Fraction> score = new SimpleObjectProperty<>(new Fraction(0,1));
    transient private ObjectProperty<Position> positionProperty = new SimpleObjectProperty<>();
    transient private ObjectProperty<Paint> fillProperty = new SimpleObjectProperty<>();
    transient private BooleanProperty isSelectedProperty = new SimpleBooleanProperty();
    transient private StringProperty shortName = new SimpleStringProperty();
    transient private StringProperty name = new SimpleStringProperty();

    public Player (Position position, String name, String shortName, Color color) {
        setPosition(position);
        setName(name);
        setShortName(shortName);
        setFill(color);
    }
    public Player(Position position) {
        setPosition(position);
    }
    public Player() {}

    //Bewegt den Spieler
    public Player moveDirection(Actions direction) {
        getPosition().moveDirection(direction);
        return this;
    }

    //gibt position des Spielers nach einer Bewegung
    public Player peekDirection(Actions direction) {
        return new Player(getPosition().peekDirection(direction));
    }

    // Name
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
    public void setIsSelected(Boolean selected) { isSelectedProperty.set(selected); }

    // Fill
    public Paint getFill() { return fillProperty.get(); }
    public ObjectProperty<Paint> getFillProperty() {
        return fillProperty;
    }
    public void setFill(Paint color) {
        fillProperty.set(color);
    }

    // Position
    public Position getPosition() { return positionProperty.get(); }
    public ObjectProperty<Position> getPositionProperty() {
        return positionProperty;
    }
    public void setPosition(Position position) {
        positionProperty.set(position);
    }

    @Override
    public String toString() {
        return this.getShortName();
    }

    // https://stackoverflow.com/a/45655071
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(getName());
        s.writeUTF(getShortName());
        s.writeUTF(getFill().toString());
        s.writeBoolean(isSelected());
        s.writeObject(getPosition());
        s.writeObject(getScore());
    }

    // https://stackoverflow.com/a/45655071
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        name = new SimpleStringProperty();
        shortName = new SimpleStringProperty();
        fillProperty = new SimpleObjectProperty<>();
        isSelectedProperty = new SimpleBooleanProperty();
        positionProperty = new SimpleObjectProperty<>();
        score = new SimpleObjectProperty<>();
        setName(s.readUTF());
        setShortName(s.readUTF());
        setFill(Color.web(s.readUTF()));
        setIsSelected(s.readBoolean());
        setPosition((Position)s.readObject());
        setScore((Fraction)s.readObject());
    }
}
