package main.java.model.ingame.entity;

import main.java.model.ingame.Direction;
import main.java.model.ingame.MapComponentModel;

public class MovingEntityModel extends MapComponentModel{
    private double speed;
    private Direction direction;
    private MovementController controller;

    public MovingEntityModel(double x, double y, double speed, MovementController controller) {
        super(x, y);
        this.speed = speed;
        this.controller = controller;
        this.direction = Direction.NONE;
    }

    public void move() {
        switch (direction) {
            case NORTH:
                setY(getY() - speed);
                break;
            case SOUTH:
                setY(getY() + speed);
                break;
            case EAST:
                setX(getX() + speed);
                break;
            case WEST:
                setX(getX() - speed);
                break;
            case NONE:
                break;
        }
    }

    public void updateDirection() {
        direction = controller.getNextDirection();
    }



}
