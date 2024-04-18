package model.ingame.entity;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import model.ingame.GameModel;
import model.ingame.physics.BlockedMovementEvent;
import model.ingame.physics.BlockedMovementListener;
import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;
import util.Coordinates;

public abstract class CollisionEntityModel extends EntityModel implements ICollisionEntity {
    private final List<CollisionListener> collisionListeners = new ArrayList<>();
    private final List<BlockedMovementListener> blockedMovementListeners = new ArrayList<>();
    private final Rectangle2D collisionBox;

    public CollisionEntityModel(Coordinates pos, double width, double height, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.collisionBox = new Rectangle2D.Double(pos.x(), pos.y(), width, height);
        setPos(pos);
    }

    @Override
    public void setPos(Coordinates pos) {
        gameModel.getMapModel().removeCollidableAt(this, (int) this.pos.x(), (int)this.pos.y());
        super.setPos(pos);
        setCollisionBox(pos.x(), pos.y());
        gameModel.getMapModel().addCollidableAt(this, (int) this.pos.x(), (int)this.pos.y());
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
    public void addBlockedMovementListener(BlockedMovementListener listener) {
        blockedMovementListeners.add(listener);
    }

    @Override
    public void notifyBlockedMovementListeners(BlockedMovementEvent e) {
        for (BlockedMovementListener listener : blockedMovementListeners) {
            listener.onMovementBlocked(e);
        }
    }

    @Override
    public void notifyCollisionListeners(CollisionEvent e) {
        for (CollisionListener listener : collisionListeners) {
            listener.onCollision(e);
        }
    }

    public void removeCollisionListener(CollisionListener listener) {
        collisionListeners.remove(listener);
    }

    @Override
    public void despawn() {
        super.despawn();
        gameModel.getMapModel().removeCollidableAt(this, (int) this.pos.x(), (int)this.pos.y());
        gameModel.removeCollisionEntity(this);
    }

    private void setCollisionBox(double x, double y) {
        collisionBox.setRect(x, y, collisionBox.getWidth(), collisionBox.getHeight());
    }
}