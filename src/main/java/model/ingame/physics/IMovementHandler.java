package model.ingame.physics;

import model.ingame.Coordinates;
import model.ingame.IUpdateable;
import model.ingame.entity.IEntity;

public interface IMovementHandler extends IUpdateable {

    Coordinates getDirectionVector();
    void setDirectionVector(Coordinates directionVector);
    double getSpeed();
    void setSpeed(double speed);
    IEntity getEntity();
    void setMoving(boolean moving);
    boolean isMoving();

}
