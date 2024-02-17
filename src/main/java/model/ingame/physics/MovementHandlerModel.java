package model.ingame.physics;

import model.ingame.Coordinates;
import model.ingame.IUpdateable;
import model.ingame.entity.IMobileEntity;

public class MovementHandlerModel<T extends IMobileEntity> implements IMovementHandler{
    private T entity;
    private double speed;
    private Coordinates directionVector = Coordinates.ZERO;
    private PhysicsEngineModel physicsEngine;

    public MovementHandlerModel(T entity, PhysicsEngineModel physicsEngine) {
        this.entity = entity;
        this.physicsEngine = physicsEngine;
    }

    public void update() {
        Coordinates pos = entity.getPos();
        Coordinates newPos = new Coordinates(pos.x + directionVector.x * speed, pos.y + directionVector.y * speed);
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
    
}
