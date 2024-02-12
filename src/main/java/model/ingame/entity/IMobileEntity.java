package model.ingame.entity;

import model.ingame.IUpdateable;
import model.ingame.physics.IMovementHandler;

public interface IMobileEntity extends ICollisionEntity, IUpdateable{
    
    IMovementHandler getMovementHandler();

}
