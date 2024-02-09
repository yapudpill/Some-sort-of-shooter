package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.MapComponentModel;

/*
 * This class is a model for moving components in the game
 */
public abstract class MovingComponentModel extends MapComponentModel{
    protected Coordinates velocityVector;
    protected double speed;

    public MovingComponentModel(Coordinates pos, double speed, Coordinates velocityVector) {
        super(pos);
        if (pos == null || velocityVector == null)
            throw new IllegalArgumentException("Position and velocity vector cannot be null");
        this.speed = speed;
        this.velocityVector = new Coordinates(0, 0);
    }

    public MovingComponentModel(Coordinates pos) {
        super(pos);
        this.speed = 0;
        this.velocityVector = new Coordinates(0, 0);
    }

    public void move() {
        pos.x += velocityVector.x * speed;
        pos.y += velocityVector.y * speed;
    }

    public void setVelocityVector(Coordinates velocityVector) {
        this.velocityVector = velocityVector;
    }

    public Coordinates getVelocityVector() {
        return velocityVector;
    }
    
    public abstract void update();
}
