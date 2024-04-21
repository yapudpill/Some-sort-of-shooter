package model.ingame.entity.behavior;

import util.Coordinates;

class Node {
    private final Coordinates coordinates;
    private int value;

    Node(Coordinates coordinates, int value) {
        this.coordinates = coordinates;
        this.value = value;
    }

    Coordinates getCoordinates() {
        return coordinates;
    }

    void setValue(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

}
