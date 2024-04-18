package model.ingame.entity;

import model.ingame.physics.MovementHandler;
import util.IUpdateable;

public interface IMobileEntity extends ICollisionEntity, IUpdateable {
    MovementHandler getMovementHandler();
}
