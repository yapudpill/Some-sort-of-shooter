package model.ingame.entity.behavior;

import model.ingame.Coordinates;

public class Node {
    private final Coordinates coordinates;
    private boolean walkable;
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

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

}
