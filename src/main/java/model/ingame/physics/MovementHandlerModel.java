package model.ingame.physics;

import model.ingame.Coordinates;
import model.ingame.entity.IMobileEntity;

/*
 * The MovementHandlerModel class is used to handle the movement of a <code>IMobleEntity</code>.
 */
public class MovementHandlerModel<T extends IMobileEntity> implements IMovementHandler{
    private T entity;
    private double speed;
    private Coordinates directionVector = new Coordinates(0, 0);
    private PhysicsEngineModel physicsEngine;
    private boolean isMoving = false;

    public MovementHandlerModel(T entity, PhysicsEngineModel physicsEngine) {
        this.entity = entity;
        this.physicsEngine = physicsEngine;
    }

    public void update() {
        Coordinates pos = entity.getPos();
        Coordinates directionNormalized = directionVector.normalize();
        Coordinates newPos = new Coordinates(pos.x + directionNormalized.x * speed, pos.y + directionNormalized.y * speed);
        physicsEngine.move(entity, newPos);
    }

    public Coordinates getDirectionVector() {
        return directionVector;
    }

    public void setDirectionVector(Coordinates directionVector) {
        this.directionVector = directionVector;
    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

}
