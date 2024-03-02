package model.ingame.entity;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;

public abstract class CollisionEntityModel extends EntityModel implements ICollisionEntity {
    private final List<CollisionListener> collisionListeners = new ArrayList<>();
    private final Rectangle2D collisionBox;

    public CollisionEntityModel(Coordinates pos, double width, double height, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.collisionBox = new Rectangle2D.Double(pos.x, pos.y, width, height);
    }

    @Override
    public void setPos(Coordinates pos) {
        gameModel.getMapModel().removeCollidableAt(this, (int) this.pos.x, (int)this.pos.y);
        super.setPos(pos);
        setColisionBox(pos.x, pos.y);
        gameModel.getMapModel().addCollidableAt(this, (int) this.pos.x, (int)this.pos.y);
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

    private void setColisionBox(double x, double y) {
        collisionBox.setRect(x, y, collisionBox.getWidth(), collisionBox.getHeight());
    }

}