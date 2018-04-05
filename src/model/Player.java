package model;

import javafx.beans.property.StringProperty;

/**
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 08.01.2018
 */

public class Player {

    private String name;
    private String shortName;

    Fraction score = new Fraction(0,1);
    Position position;

    Player (int posX, int posY) {
        this.position = new Position(posX, posY);
    }

    Player (Position position) {
        this.position = position;
    }

    Player (Position position, String name, String shortName) {
        this.position = position;
        this.name = name;
        this.shortName = shortName;
    }

    public Player moveDirection(Direction direction) {
        position.moveDirection(direction);
        return this;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public Player peekDirection(Direction direction) {
        return new Player(position.peekDirection(direction));
    }

    @Override
    public String toString() {
        return this.shortName;
    }
}
