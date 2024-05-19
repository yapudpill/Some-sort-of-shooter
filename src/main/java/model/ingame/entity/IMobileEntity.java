package model.ingame.entity;

import model.ingame.physics.MovementHandler;
import util.IUpdateable;

/**
 *  Interface for entities that can move.
 */
public interface IMobileEntity extends ICollisionEntity, IUpdateable {
    MovementHandler getMovementHandler();
}
