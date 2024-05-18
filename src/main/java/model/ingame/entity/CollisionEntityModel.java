package model.ingame.entity;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

import model.ingame.GameModel;
import model.ingame.physics.BlockedMovementEvent;
import model.ingame.physics.BlockedMovementListener;
import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;
import model.ingame.physics.ExitEvent;
import model.ingame.physics.ExitListener;
import util.Coordinates;

public abstract class CollisionEntityModel extends EntityModel implements ICollisionEntity {
    private final List<CollisionListener> collisionListeners = new ArrayList<>();
    private final List<BlockedMovementListener> blockedMovementListeners = new ArrayList<>();
    private final List<ExitListener> exitListeners = new ArrayList<>();
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

    public void addExitListener(ExitListener listener) {
        exitListeners.add(listener);
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

    public void notifyExitListeners(ExitEvent e) {
        for (ExitListener listener : exitListeners) {
            listener.onExit(e);
        }
    }

    @Override
    public void despawn() {
        super.despawn();
        gameModel.getMapModel().removeCollidableAt(this, this.pos);
        gameModel.removeCollisionEntity(this);
    }

    // returns the list of the entities who exited the collision
    public Set<ICollisionEntity> updateCollidedEntities(Set<ICollisionEntity> collidedEntities) {
        // Calculate the intersection of the two sets
        Set<ICollisionEntity> exitEntities = new CopyOnWriteArraySet<>(currentlyCollidedEntities);
        exitEntities.removeAll(collidedEntities);
        currentlyCollidedEntities.clear();
        currentlyCollidedEntities.addAll(collidedEntities);
        return exitEntities;
    }

    public boolean isCurrentlyCollidingWith(ICollisionEntity entity) {
        return currentlyCollidedEntities.contains(entity);
    }

    public boolean isCurrentlyCollidingWith(Predicate<ICollisionEntity> predicate) {
        return currentlyCollidedEntities.stream().anyMatch(predicate);
    }


}