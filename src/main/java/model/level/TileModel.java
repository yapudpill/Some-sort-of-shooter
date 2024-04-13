package model.level;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;

public abstract class TileModel implements ITileModel {
    protected final Set<ICollisionEntity> collidables = new CopyOnWriteArraySet<>();
    protected Set<Predicate<IEntity>> canEnterConditions = new CopyOnWriteArraySet<>();

    public abstract boolean isWalkable();

    public boolean canEnter(IEntity entity) {
        if(canEnterConditions.isEmpty()) return true;
        return canEnterConditions.stream().allMatch(condition -> condition.test(entity));
    }

    @Override
    public void applyEnterEffect(IEntity entity) {
    }

    public void addCollidable(ICollisionEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        collidables.add(entity);
    }

    public void removeCollidable(ICollisionEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        collidables.remove(entity);
    }

    public Set<ICollisionEntity> getCollidablesSet() {
        return new CopyOnWriteArraySet<ICollisionEntity>(collidables);
    }

    public void reset() {
        collidables.clear();
    }

    public void printCollidables() {
        System.out.println(collidables);
    }

    public void addCanEnterCondition(Predicate<IEntity> condition) {
        canEnterConditions.add(condition);
    }

    public void removeCanEnterCondition(Predicate<IEntity> condition) {
        canEnterConditions.remove(condition);
    }
}
