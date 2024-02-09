package model.ingame;

/*
 * The Coordinates class is used to represent a point in a 2D plane.
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


}
