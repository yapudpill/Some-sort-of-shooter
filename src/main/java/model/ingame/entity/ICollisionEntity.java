package model.ingame.entity;

import java.awt.geom.Rectangle2D;
import java.util.Set;

import model.ingame.physics.BlockedMovementEvent;
import model.ingame.physics.BlockedMovementListener;
import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;
import model.ingame.physics.ExitEvent;
import model.ingame.physics.ExitListener;
import util.Coordinates;

public interface ICollisionEntity extends IEntity {
    Rectangle2D getCollisionBox();
    void setCollisionBox(Coordinates pos);
    void addCollisionListener(CollisionListener listener);
    void addBlockedMovementListener(BlockedMovementListener listener);
    void addExitListener(ExitListener listener);
    void notifyCollisionListeners(CollisionEvent e);
    void notifyBlockedMovementListeners(BlockedMovementEvent e);
    void notifyExitListeners(ExitEvent e);
    Set<ICollisionEntity> updateCollidedEntities(Set<ICollisionEntity> collidedEntities);
}
