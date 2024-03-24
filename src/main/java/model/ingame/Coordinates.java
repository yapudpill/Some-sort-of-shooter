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

    public Coordinates(Coordinates other) {
        this.x = other.x;
        this.y = other.y;
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
        if(length == 0) return new Coordinates(0, 0);
        return new Coordinates(x / length, y / length);
    }

    public Coordinates translate(Coordinates other) {
        return new Coordinates(x + other.x, y + other.y);
    }

    public Coordinates multiply(double scalar) {
        return new Coordinates(x * scalar, y * scalar);
    }

    public Coordinates rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Coordinates(x * cos - y * sin, x * sin + y * cos);
    }

    public boolean isInCenter() {
        double centerX = (int) x + 0.5;
        double centerY = (int) y + 0.5;
        return Math.abs(x- centerX) < 0.1 && Math.abs(y - centerY) < 0.1;
    }

    public boolean isZero(){
        return x == 0 && y == 0;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
