package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.physics.BlockedMovementEvent;
import model.ingame.physics.BlockedMovementListener;
import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;
import util.Coordinates;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

public abstract class CollisionEntityModel extends EntityModel implements ICollisionEntity {
    private final List<CollisionListener> collisionListeners = new ArrayList<>();
    private final List<BlockedMovementListener> blockedMovementListeners = new ArrayList<>();
    private final Rectangle2D collisionBox;
    private final Set<ICollisionEntity> currentlyCollidedEntities = new CopyOnWriteArraySet<>();

    public CollisionEntityModel(Coordinates pos, double width, double height, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.collisionBox = new Rectangle2D.Double(pos.x(), pos.y(), width, height);
        setPos(pos);
    }

    @Override
    public void setPos(Coordinates pos) {
        gameModel.getMapModel().removeCollidableAt(this, this.pos);
        super.setPos(pos);
        setCollisionBox(pos);
        gameModel.getMapModel().addCollidableAt(this, this.pos);
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return collisionBox;
    }

    @Override
    public void setCollisionBox(Coordinates pos) {
        collisionBox.setRect(pos.x(), pos.y(), collisionBox.getWidth(), collisionBox.getHeight());
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

    @Override
    public void despawn() {
        super.despawn();
        gameModel.getMapModel().removeCollidableAt(this, this.pos);
        gameModel.removeCollisionEntity(this);
    }

    public void updateCollidedEntities(Set<ICollisionEntity> collidedEntities) {
        currentlyCollidedEntities.clear();
        currentlyCollidedEntities.addAll(collidedEntities);
    }

    public boolean isCurrentlyCollidingWith(ICollisionEntity entity) {
        return currentlyCollidedEntities.contains(entity);
    }

    public boolean isCurrentlyCollidingWith(Predicate<ICollisionEntity> predicate) {
        return currentlyCollidedEntities.stream().anyMatch(predicate);
    }


}