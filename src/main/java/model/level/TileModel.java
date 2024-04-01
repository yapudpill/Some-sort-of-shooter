package model.level;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;

public abstract class TileModel implements ITileModel {
    protected final Set<ICollisionEntity> collidables = new CopyOnWriteArraySet<>();

    public abstract boolean isWalkable();

    public boolean canEnter(IEntity entity) {
        return true;
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
}
