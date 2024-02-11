package model.ingame.entity;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;

public interface ICollisionEntity extends IEntity{
    
    Rectangle2D getCollisionBox();
    void addCollisionListener(CollisionListener listener);
    boolean hasCollisionListeners();
    Iterator<CollisionListener> getCollisionListenersIterator();
    void notifyCollisionListeners(CollisionEvent e);

}
