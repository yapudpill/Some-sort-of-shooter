package model.ingame.entity.behavior;

import util.Coordinates;

/**
 * Represents a node in the grid used by the flood fill algorithm.
 */
public class Node {
    private final Coordinates coordinates;
    private int value;

    public Node(Coordinates coordinates, int value) {
        this.coordinates = coordinates;
        this.value = value;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
