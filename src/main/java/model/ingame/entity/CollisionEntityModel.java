package model.ingame.entity;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.ingame.Coordinates;
import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;

public abstract class CollisionEntityModel extends EntityModel implements ICollisionEntity {
    private final List<CollisionListener> collisionListeners = new ArrayList<>();
    private final Rectangle2D collisionBox;

    public CollisionEntityModel(Coordinates pos, double width, double height) {
        super(pos, width, height);
        this.collisionBox = new Rectangle2D.Double(pos.x, pos.y, width, height);
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return collisionBox;
    }

    @Override
    public void addCollisionListener(CollisionListener listener) {
        collisionListeners.add(listener);
    }

    @Override
    public Iterator<CollisionListener> getCollisionListenersIterator() {
        return collisionListeners.iterator();
    }

    @Override
    public void notifyCollisionListeners(CollisionEvent e) {
        for (CollisionListener listener : collisionListeners) {
            listener.onCollision(e);
        }
    }

    @Override
    public boolean hasCollisionListeners() {
        return !collisionListeners.isEmpty();
    }

    public void removeCollisionListener(CollisionListener listener) {
        collisionListeners.remove(listener);
    }

    public void setColisionBox(double x, double y) {
        collisionBox.setRect(x, y, width, height);
    }

}