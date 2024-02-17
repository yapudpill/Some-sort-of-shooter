package model.ingame;

/*
 * The Coordinates class is used to represent a point/vector in a 2D plane.
 */

public class Coordinates {
    public double x;
    public double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /*
     * Constants for the four cardinal directions.
     */

    public static final Coordinates UP = new Coordinates(0, -1);
    public static final Coordinates DOWN = new Coordinates(0, 1);
    public static final Coordinates LEFT = new Coordinates(-1, 0);
    public static final Coordinates RIGHT = new Coordinates(1, 0);
    public static final Coordinates ZERO = new Coordinates(0, 0);

    public boolean intEquals(Coordinates other) {
        return (int) x == (int) other.x && (int) y == (int) other.y;
    }

    public void add(Coordinates addedVelocityVector) {
        x += addedVelocityVector.x;
        y += addedVelocityVector.y;
    }

    public Coordinates opposite() {
        return new Coordinates(-x, -y);
    }

    public Coordinates normalize() {
        double length = Math.sqrt(x * x + y * y);
        return new Coordinates(x / length, y / length);
    }

    public Coordinates translate(Coordinates other) {
        return new Coordinates(x + other.x, y + other.y);
    }

    public Coordinates multiply(double scalar) {
        return new Coordinates(x * scalar, y * scalar);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
