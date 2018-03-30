/**
 * Class representing 2D Coordinates
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 08.01.2018
 */
public class Position implements Comparable<Position> {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position moveDirection(Direction direction) {
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

    public Position add(Position pos) {
        return new Position(this.x + pos.x, this.y + pos.y);
    }

    public Position subtract(Position pos) {
        return new Position(this.x - pos.x, this.y - pos.y);
    }

    public int area() {
        return x * y;
    }

    @Override
    public boolean equals(Object obj) {
        return x == ((Position)obj).x && y == ((Position)obj).y;
    }

    public Position peekDirection(Direction direction) {
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