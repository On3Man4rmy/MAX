package model;

import java.io.Serializable;

/**
 * Class representing 2D Coordinates
 * @author  Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 3.0 19/04/2018
 */
public class Position implements Comparable<Position>, Serializable {
    public int x;
    public int y;

    //Setzt Position
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //bewegt position
    public Position moveDirection(Actions direction) {
        switch (direction) {
            case UP:
                y--;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            case DOWN:
                y++;
                break;
        }

        return this;
    }



    @Override
    public boolean equals(Object obj) {
        return x == ((Position)obj).x && y == ((Position)obj).y;
    }
    //gibt position nach möglicher bewegung
    public Position peekDirection(Actions direction) {
        return new Position(x, y).moveDirection(direction);
    }

    @Override
    public int compareTo(Position o) {
        int result = 0;
        if(y > o.y) {
            result = 1;
        } else if (y == o.y) {
            if(x > o.x) {
                result = 1;
            } else if (x < o.x) {
                result = -1;
            }
        } else {
            result = -1;
        }

        return result;
    }
}