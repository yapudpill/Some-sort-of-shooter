package model.level;

import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

/**
 * Represents a tile in the game. It decides which entities can enter it and what happens when they do.
 */
public abstract class TileModel {
    protected final Set<ICollisionEntity> collidables = new CopyOnWriteArraySet<>();
    protected Set<Predicate<IEntity>> canEnterConditions = new CopyOnWriteArraySet<>();

    public void applyEnterEffect(IEntity entity) {}

    public boolean canEnter(IEntity entity) {
        return canEnterConditions.stream().allMatch(condition -> condition.test(entity));
    }

    public void addCanEnterCondition(Predicate<IEntity> condition) {
        canEnterConditions.add(condition);
    }

    public void removeCanEnterCondition(Predicate<IEntity> condition) {
        canEnterConditions.remove(condition);
    }

    public void addCollidable(ICollisionEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        collidables.add(entity);
    }

    public void removeCollidable(ICollisionEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        collidables.remove(entity);
    }

    public Set<ICollisionEntity> getCollidablesSet() {
        return new CopyOnWriteArraySet<ICollisionEntity>(collidables);
    }

    /**
     * For debug
     */
    public void printCollidables() {
        System.out.println(collidables);
    }
}
