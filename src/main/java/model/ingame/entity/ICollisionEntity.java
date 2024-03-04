package model.ingame.entity;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;

public interface ICollisionEntity extends IEntity{

    Rectangle2D getCollisionBox();
    void addCollisionListener(CollisionListener listener);
    void removeCollisionListener(CollisionListener listener);
    Iterator<CollisionListener> getCollisionListenersIterator();
    void notifyCollisionListeners(CollisionEvent e);
    default boolean hasCollisionListeners(){
        return getCollisionListenersIterator().hasNext();
    }

}
