package model.ingame.physics;

import model.ingame.entity.IMobileEntity;
import util.Coordinates;
import util.IUpdateable;

/*
 * The MovementHandlerModel class is used to handle the movement of a <code>IMobileEntity</code>.
 */
public class MovementHandler implements IUpdateable {
    private final IMobileEntity entity;
    private final PhysicsEngineModel physicsEngine;
    private double speed;
    private Coordinates directionVector = new Coordinates(0, 0);
    private boolean isMoving = false;

    public MovementHandler(IMobileEntity entity, PhysicsEngineModel physicsEngine) {
        this.entity = entity;
        this.physicsEngine = physicsEngine;
    }

    @Override
    public void update(double delta) {
        Coordinates movement = directionVector.normalize().multiply(speed * delta);
        physicsEngine.move(entity, movement);
    }

    public Coordinates getDirectionVector() {
        return directionVector;
    }

    public void setDirectionVector(Coordinates directionVector) {
        this.directionVector = directionVector;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isMoving() {
        return isMoving;
    }

    void setMoving(boolean moving) {
        isMoving = moving;
    }
}
