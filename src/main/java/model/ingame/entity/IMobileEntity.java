package model.ingame.entity;

import model.ingame.physics.IMovementHandler;

public interface IMobileEntity extends ICollisionEntity{
    
    IMovementHandler getMovementHandler();

}
