package model.ingame.entity;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import model.ingame.physics.BlockedMovementEvent;
import model.ingame.physics.BlockedMovementListener;
import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;

public interface ICollisionEntity extends IEntity{

    Rectangle2D getCollisionBox();
    void addCollisionListener(CollisionListener listener);
    void addBlockedMovementListener(BlockedMovementListener listener);
    void removeCollisionListener(CollisionListener listener);
    void notifyCollisionListeners(CollisionEvent e);
    void notifyBlockedMovementListeners(BlockedMovementEvent e);
}
