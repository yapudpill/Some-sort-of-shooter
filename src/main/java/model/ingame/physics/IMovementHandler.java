package model.ingame.physics;

import model.ingame.Coordinates;
import model.ingame.entity.IEntity;

public interface IMovementHandler {
    
    Coordinates getDirectionVector();
    void setDirectionVector(Coordinates directionVector);
    double getSpeed();
    void setSpeed(double speed);
    IEntity getEntity();

}
