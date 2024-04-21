package util;

/**
 * The Coordinates class is used to represent a point/vector in a 2D plane.
 */
public record Coordinates(double x, double y) {
    public static enum CardinalDirection {
        UP, DOWN, LEFT, RIGHT
    }

    // Constants for the four cardinal directions.
    public static final Coordinates UP = new Coordinates(0, -1);
    public static final Coordinates DOWN = new Coordinates(0, 1);
    public static final Coordinates LEFT = new Coordinates(-1, 0);
    public static final Coordinates RIGHT = new Coordinates(1, 0);
    public static final Coordinates ZERO = new Coordinates(0, 0);

    public Coordinates add(Coordinates other) {
        return new Coordinates(x + other.x, y + other.y);
    }

    public Coordinates multiply(double scalar) {
        return new Coordinates(x * scalar, y * scalar);
    }

    public Coordinates opposite() {
        return multiply(-1);
    }

    public Coordinates normalize() {
        double length = Math.hypot(x, y);
        return length == 0 ? ZERO : new Coordinates(x / length, y / length);
    }

    public Coordinates rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Coordinates(x * cos - y * sin, x * sin + y * cos);
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public Coordinates xProjection() {
        return new Coordinates(x, 0);
    }

    public Coordinates yProjection() {
        return new Coordinates(0, y);
    }

    public boolean isInCenter() {
        double centerX = (int) x + 0.5;
        double centerY = (int) y + 0.5;
        return Math.abs(x - centerX) < 0.1 && Math.abs(y - centerY) < 0.1;
    }

    public double distance(Coordinates other) {
        return Math.hypot(x - other.x, y - other.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public CardinalDirection getCardinalDirection() {
        if (Math.abs(x) > Math.abs(y)) {
            if (x > 0) return CardinalDirection.RIGHT;
            else return CardinalDirection.LEFT;
        } else {
            if (y > 0) return CardinalDirection.DOWN;
            else return CardinalDirection.UP;
        }
    }
}
